package org.JavaCatamaran;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.Stack;

public class PortController implements AddBoatListener {

    @FXML
    private AnchorPane formPortPane;

    @FXML
    private Canvas canvasPort;

    @FXML
    private TextField textFieldTake, textFieldNewLevelName;

    @FXML
    private ListView<String> listBoxPorts;

    private PortCollection portCollection;

    private Stack<ITransport> boatStack = new Stack<>();

    private FileChooser fileChooser = new FileChooser();

    private static Logger logger = Logger.getLogger(PortController.class);

    private void Draw() {
        GraphicsContext gr = canvasPort.getGraphicsContext2D();
        gr.clearRect(0, 0, canvasPort.getWidth(), canvasPort.getHeight());
        portCollection.getPort(listBoxPorts.getSelectionModel().getSelectedItem()).Draw(gr);
    }

    private boolean isInt(TextField textField) {
        try {
            Integer.parseInt(textField.getText());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void ReloadLevels() {
        int index = listBoxPorts.getSelectionModel().getSelectedIndex();
        listBoxPorts.getItems().clear();
        for (int i = 0; i < portCollection.getKeys().size(); i++) {
            listBoxPorts.getItems().add(portCollection.getKeys().get(i));
        }
        if (listBoxPorts.getItems().size() > 0 && (index == -1 || index >= listBoxPorts.getItems().size())) {
            listBoxPorts.getSelectionModel().selectFirst();
        } else if (listBoxPorts.getItems().size() > 0 && index > -1 && index < listBoxPorts.getItems().size()) {
            listBoxPorts.getSelectionModel().select(index);
        }
    }

    @FXML
    void buttonAddPortClick() {
        if (textFieldNewLevelName.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Enter port name");
            alert.showAndWait();
            return;
        }
        logger.info("Added port " + textFieldNewLevelName.getText());
        portCollection.AddParking(textFieldNewLevelName.getText());
        ReloadLevels();
    }

    @FXML
    void buttonDelPortClick() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Message");
        alert.setHeaderText(null);
        alert.setContentText("Delete port " + listBoxPorts.getSelectionModel().getSelectedItem() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            logger.info("Deleted port " + listBoxPorts.getSelectionModel().getSelectedItem());
            portCollection.DelParking(listBoxPorts.getSelectionModel().getSelectedItem());
            ReloadLevels();
        }
    }


    @FXML
    void buttonAddBoatClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(PortController.class.getResource("FormBoatConfig.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        //end loading
        ConfigController configController = loader.getController();
        configController.AddEvent(this);
        formPortPane.setDisable(true);
        stage.showAndWait();
        formPortPane.setDisable(false);
        stage.close();
    }

    @FXML
    void buttonTakeBoatClick() {
        if (!textFieldTake.getText().equals("") && isInt(textFieldTake) && listBoxPorts.getSelectionModel().getSelectedIndex() > -1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            try {
                var boat = portCollection.getPort(listBoxPorts.getSelectionModel().getSelectedItem()).takeObject(Integer.parseInt(textFieldTake.getText()));
                if (boat != null) {
                    logger.info("Seized transport " + boat + " from place " + textFieldTake.getText());
                    boatStack.push(boat);
                }
                Draw();
            } catch (PortNotFoundException ex) {
                logger.warn(ex);
                alert.setTitle("Not Found");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            } catch (Exception ex) {
                logger.fatal(ex);
                alert.setTitle("Unknown error");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        }
    }

    @FXML
    void buttonDriveClick() throws IOException {
        if (!boatStack.isEmpty()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FormCatamaran.fxml"));
            Parent root = loader.load();
            CatamaranController catamaranController = loader.getController();
            catamaranController.setBoat(boatStack.pop());
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            //end loading
            formPortPane.setDisable(true);
            stage.setAlwaysOnTop(true);
            stage.showAndWait();
            stage.setAlwaysOnTop(false);
            formPortPane.setDisable(false);
            stage.close();
        }
    }

    public void addBoat(Vehicle boat) {
        if (boat != null && listBoxPorts.getSelectionModel().getSelectedIndex() > -1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            try {
                if (portCollection.getPort(listBoxPorts.getSelectionModel().getSelectedItem()).append(boat)) {
                    logger.info("Add transport " + boat);
                    Draw();
                } else {
                    alert.setTitle("Error");
                    alert.setContentText("Cant moor transport");
                    alert.showAndWait();
                }
            } catch (PortOverflowException ex) {
                logger.warn(ex);
                alert.setTitle("Overflow");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            } catch (PortAlreadyHaveException ex) {
                logger.warn(ex);
                alert.setTitle("Already have");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            } catch (Exception ex) {
                logger.fatal(ex);
                alert.setTitle("Unknown error");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        }
    }

    @FXML
    void saveMenuItemClick() {
        Window stage = canvasPort.getScene().getWindow();
        fileChooser.setTitle("Save port");
        fileChooser.setInitialFileName("");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("text files", "*.txt"));
        try {
            File file = fileChooser.showSaveDialog(stage);
            if (file == null) {
                return;
            }
            portCollection.SaveData(file.getAbsolutePath());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Done!");
            alert.showAndWait();
        } catch (IOException ex) {
            logger.error(ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error while saving");
            alert.showAndWait();
        } catch (Exception ex) {
            logger.fatal(ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Unknown error while saving");
            alert.showAndWait();
        }
    }

    @FXML
    void loadMenuItemClick() {
        Window stage = canvasPort.getScene().getWindow();
        fileChooser.setTitle("Load port");
        fileChooser.setInitialFileName("");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("text files", "*.txt"));
        Alert alertError = new Alert(Alert.AlertType.ERROR);
        alertError.setHeaderText(null);
        try {
            File file = fileChooser.showOpenDialog(stage);
            if (file == null) {
                return;
            }
            portCollection.LoadData(file.getAbsolutePath());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Done!");
            alert.showAndWait();
            logger.info("Loaded from file " + file.getName());
            ReloadLevels();
            Draw();
        } catch (FileNotFoundException ex) {
            logger.warn(ex);
            alertError.setTitle("File not found");
            alertError.setContentText(ex.getMessage());
            alertError.showAndWait();
        } catch (PortOverflowException ex) {
            logger.warn(ex);
            alertError.setTitle("Port is full");
            alertError.setContentText(ex.getMessage());
            alertError.showAndWait();
        } catch (ClassFormatError ex) {
            logger.error(ex);
            alertError.setTitle("Error");
            alertError.setContentText("Incorrect format");
            alertError.showAndWait();
        } catch (Exception ex) {
            logger.fatal(ex);
            alertError.setTitle("Unknown error while loading");
            alertError.setContentText(ex.getMessage());
            alertError.showAndWait();
        }
    }

    @FXML
    void savePortMenuClick() {
        Window stage = canvasPort.getScene().getWindow();
        fileChooser.setTitle("Save port");
        fileChooser.setInitialFileName("");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("text files", "*.txt"));
        try {
            File file = fileChooser.showSaveDialog(stage);
            if (portCollection.savePort(file.getAbsolutePath(), listBoxPorts.getSelectionModel().getSelectedItem())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("Done!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to save port");
                alert.showAndWait();
            }
        } catch (Exception ex) {
            logger.fatal(ex);
        }
    }

    @FXML
    void loadPortMenuItemClick() {
        Window stage = canvasPort.getScene().getWindow();
        fileChooser.setTitle("Load port");
        fileChooser.setInitialFileName("");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("text files", "*.txt"));
        try {
            File file = fileChooser.showOpenDialog(stage);
            if (portCollection.loadPort(file.getAbsolutePath())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("Done!");
                alert.showAndWait();
                ReloadLevels();
                Draw();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to load port");
                alert.showAndWait();
            }
        } catch (Exception ex) {
            logger.fatal(ex);
        }
    }

    @FXML
    void buttonSortClick() {
        if (listBoxPorts.getSelectionModel().getSelectedIndex() > -1) {
            portCollection.getPort(listBoxPorts.getSelectionModel().getSelectedItem()).sort();
            Draw();
            logger.info("Sorting levels");
        }
    }

    @FXML
    void initialize() throws IOException {
        fileChooser.setInitialDirectory(new File("D:\\"));
        portCollection = new PortCollection((int) canvasPort.getWidth(), (int) canvasPort.getHeight());
        listBoxPorts.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals(-1)) {
                logger.info("Went to port " + listBoxPorts.getSelectionModel().getSelectedItem());
                Draw();
            }
        });
    }
}
