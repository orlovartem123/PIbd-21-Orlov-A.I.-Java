package JavaCatamaran;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class Controller {

    private ITransport boat;

    private SailForm sailForm = SailForm.TRIANGLE;

    @FXML
    private CheckBox triangleCheck;

    @FXML
    private CheckBox diamondCheck;

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
        boat.DrawTransport(gc);
    }


    @FXML
    void buttonCreateCatamaranClick(ActionEvent event) {
        int bobsNum = 1;
        try {
            bobsNum = Integer.parseInt(setBobsField.getText());
        } catch (Exception e) {
        }
        Random rnd = new Random();
        boat = new Catamaran(rnd.nextInt(300) + 100, rnd.nextInt(2000) + 1000, Color.FORESTGREEN, Color.RED, true, bobsNum, sailForm);
        boat.SetPosition(rnd.nextInt(100) + 10, rnd.nextInt(100) + 10, (int) canvas.getWidth(),
                (int) canvas.getHeight());
        Draw();
    }

    @FXML
    void buttonCreateBoatClick(ActionEvent event) {
        Random rnd = new Random();
        boat = new Boat(rnd.nextInt(300) + 100, rnd.nextInt(2000) + 1000, Color.FORESTGREEN);
        boat.SetPosition(rnd.nextInt(100) + 10, rnd.nextInt(100) + 10, (int) canvas.getWidth(),
                (int) canvas.getHeight());
        Draw();
    }

    @FXML
    void buttonMoveClick(ActionEvent event) {
        //получаем имя кнопки
        if (boat != null) {
            String name = event.getSource().toString();
            switch (name) {
                case "Button[id=buttonUp, styleClass=button]''":
                    boat.MoveTransport(Direction.Up);
                    break;
                case "Button[id=buttonDown, styleClass=button]''":
                    boat.MoveTransport(Direction.Down);
                    break;
                case "Button[id=buttonLeft, styleClass=button]''":
                    boat.MoveTransport(Direction.Left);
                    break;
                case "Button[id=buttonRight, styleClass=button]''":
                    boat.MoveTransport(Direction.Right);
                    break;
            }
            Draw();
        }
    }

    @FXML
    void checkBoxesChecked(ActionEvent event) {
        String name = event.getSource().toString();
        if (name.equals("CheckBox[id=triangleCheck, styleClass=check-box]'Triangle'")) {
            sailForm = SailForm.TRIANGLE;
            triangleCheck.setSelected(true);
            diamondCheck.setSelected(false);
        }
        if (name.equals("CheckBox[id=diamondCheck, styleClass=check-box]'Diamond'")) {
            sailForm = SailForm.DIAMOND;
            diamondCheck.setSelected(true);
            triangleCheck.setSelected(false);
        }
    }

    @FXML
    void initialize() {


    }
}
