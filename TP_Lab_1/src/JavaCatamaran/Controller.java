package JavaCatamaran;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class Controller {
    private Catamaran catamaran;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane backgroundPane;

    @FXML
    private Button buttonCreate;

    @FXML
    private Button buttonUp;

    @FXML
    private Button buttonLeft;

    @FXML
    private Button buttonRight;

    @FXML
    private Button buttonDown;

    @FXML
    private TextField setBobsField;

    @FXML
    private Canvas canvas;

    private void Draw() {
        var gc = canvas.getGraphicsContext2D();
        catamaran.DrawTransport(gc);
    }


    @FXML
    void buttonCreateClick(ActionEvent event) {
        int bobsNum=1;
        try{
            bobsNum=Integer.parseInt(setBobsField.getText());
        }catch (Exception e){}


        Random rnd = new Random();
        catamaran = new Catamaran(rnd.nextInt(300) + 100, rnd.nextInt(2000) + 1000, Color.FORESTGREEN, Color.RED, true, true, bobsNum);
        catamaran.SetPosition(rnd.nextInt(100) + 10, rnd.nextInt(100) + 10, (int) canvas.getWidth(),
                (int) canvas.getHeight());
        Draw();
    }

    @FXML
    void buttonMoveClick(ActionEvent event) {
        //получаем имя кнопки
        String name = event.getSource().toString();
        switch (name) {
            case "Button[id=buttonUp, styleClass=button]''":
                catamaran.MoveTransport(Direction.Up);
                break;
            case "Button[id=buttonDown, styleClass=button]''":
                catamaran.MoveTransport(Direction.Down);
                break;
            case "Button[id=buttonLeft, styleClass=button]''":
                catamaran.MoveTransport(Direction.Left);
                break;
            case "Button[id=buttonRight, styleClass=button]''":
                catamaran.MoveTransport(Direction.Right);
                break;
        }
        Draw();
    }

    @FXML
    void initialize() {


    }
}
