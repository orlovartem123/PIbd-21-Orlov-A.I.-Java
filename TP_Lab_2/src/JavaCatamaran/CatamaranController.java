package JavaCatamaran;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.util.Random;

public class CatamaranController {

    private ITransport boat;

    private SailForm sailForm = SailForm.TRIANGLE;

    @FXML
    private CheckBox triangleCheck, diamondCheck;

    @FXML
    private TextField setSailsField;

    @FXML
    private Canvas canvas;

    public void setBoat(ITransport boat) {
        this.boat = boat;
        Draw();
    }

    private void Draw() {
        var gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        if (boat != null) {
            boat.DrawTransport(gc);
        }
    }

    @FXML
    void buttonCreateCatamaranClick() {
        int sailsNum = 1;
        try {
            sailsNum = Integer.parseInt(setSailsField.getText());
        } catch (Exception ignored) {
        }
        Random rnd = new Random();
        boat = new Catamaran(rnd.nextInt(300) + 100, rnd.nextInt(2000) + 1000, Color.FORESTGREEN, Color.RED, true, true, true, sailsNum, sailForm);
        boat.SetPosition(rnd.nextInt(100) + 10, rnd.nextInt(100) + 10, (int) canvas.getWidth(),
                (int) canvas.getHeight());
        Draw();
    }

    @FXML
    void buttonCreateBoatClick() {
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
