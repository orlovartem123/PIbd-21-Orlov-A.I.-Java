package JavaCatamaran;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class PortController {

    @FXML
    private AnchorPane formPortPane;

    @FXML
    private Canvas canvasPort;

    @FXML
    private TextField textFieldTake;

    private Port<Boat, IDrawing> port;

    private void Draw() {
        GraphicsContext gr = canvasPort.getGraphicsContext2D();
        gr.clearRect(0, 0, canvasPort.getWidth(), canvasPort.getHeight());
        port.Draw(gr);
    }

    private boolean isInt(TextField textField) {
        try {
            Integer.parseInt(textField.getText());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @FXML
    void buttonSetBoatClick() throws IOException {
        Color dialogColor = ColorPickerBox.display(formPortPane);
        if (!ColorPickerBox.isCanceled) {
            var boat = new Boat(100, 1000, dialogColor);
            if (port.append(boat)) {
                Draw();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(null);
                alert.setHeaderText(null);
                alert.setContentText("Parking is full");
                alert.showAndWait();
            }
        }
    }

    @FXML
    void buttonSetCatamaranClick() throws IOException {
        Color dialogMainColor = ColorPickerBox.display(formPortPane);
        if (!ColorPickerBox.isCanceled) {
            Color dialogDopColor = ColorPickerBox.display(formPortPane);
            if (!ColorPickerBox.isCanceled) {
                var boat = new Catamaran(100, 1000, dialogMainColor, dialogDopColor, true, 3, SailForm.DIAMOND);
                if (port.append(boat)) {
                    Draw();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle(null);
                    alert.setHeaderText(null);
                    alert.setContentText("Parking is full");
                    alert.showAndWait();
                }
            }
        }
    }

    @FXML
    void buttonTakeBoatClick() throws IOException {
        if (!textFieldTake.getText().equals("") && isInt(textFieldTake)) {
            var boat = port.takeObject(Integer.parseInt(textFieldTake.getText()));
            if (boat != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FormCatamaran.fxml"));
                Parent root = loader.load();
                CatamaranController catamaranController = loader.getController();
                catamaranController.setBoat(boat);
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
            Draw();
        }
    }

    @FXML
    void initialize() {
        port = new Port<>((int) canvasPort.getWidth(),
                (int) canvasPort.getHeight());
        Draw();
    }
}
