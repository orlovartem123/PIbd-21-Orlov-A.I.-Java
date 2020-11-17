package JavaCatamaran;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class ColorPickerBox {

    private static Color color = Color.BLACK;

    public static void setColor(Color newColor) {
        color = newColor;
    }

    public static Color getColor() {
        return color;
    }

    public static boolean isCanceled = true;

    public static Color display(AnchorPane anchorPane) throws IOException {
        setToDefault();
        //loading
        FXMLLoader loader = new FXMLLoader(ColorPickerBox.class.getResource("ColorPickerBox.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        //end loading
        anchorPane.setDisable(true);
        stage.setAlwaysOnTop(true);
        stage.showAndWait();
        stage.setAlwaysOnTop(false);
        anchorPane.setDisable(false);
        stage.close();
        return color;
    }

    private static void setToDefault() {
        color = Color.BLACK;
        isCanceled = true;
    }
}
