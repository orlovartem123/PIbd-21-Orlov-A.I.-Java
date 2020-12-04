package JavaCatamaran;

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
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
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
        if (!textFieldTake.getText().equals("") && isInt(textFieldTake)) {
            var boat = portCollection.getPort(listBoxPorts.getSelectionModel().getSelectedItem()).takeObject(Integer.parseInt(textFieldTake.getText()));
            if (boat != null) {
                boatStack.push(boat);
            }
            Draw();
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
            if (portCollection.getPort(listBoxPorts.getSelectionModel().getSelectedItem()).append(boat)) {
                Draw();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Cant moor transport");
                alert.showAndWait();
            }
        }
    }

    @FXML
    void initialize() {
        portCollection = new PortCollection((int) canvasPort.getWidth(), (int) canvasPort.getHeight());
        listBoxPorts.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals(-1)) {
                Draw();
            }
        });
    }
}
