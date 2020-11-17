package JavaCatamaran;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

public class ColorPickerBoxController {

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private Button acceptButton, cancelButton;

    @FXML
    void initialize() {
        colorPicker.setValue(Color.BLACK);
        acceptButton.setOnAction(e -> {
            acceptButton.getScene().getWindow().hide();
            ColorPickerBox.setColor(colorPicker.getValue());
            ColorPickerBox.isCanceled = false;
        });
        cancelButton.setOnAction(e -> cancelButton.getScene().getWindow().hide());
    }
}
