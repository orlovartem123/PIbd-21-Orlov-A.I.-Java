package org.JavaCatamaran;

import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ConfigController {

    @FXML
    private Label labelBoat, labelCatamaran;

    @FXML
    private Spinner<Integer> spinnerSpeed, spinnerWeight;

    @FXML
    private CheckBox checkBoxBobs, checkBoxPasSets, checkBoxSails;

    @FXML
    private Pane panelRed, panelGray, panelLime, panelYellow, panelBlue, panelBlack, panelFuchsia, panelWhite;

    @FXML
    private Button buttonCancel;

    @FXML
    private Canvas canvasBoat;

    @FXML
    private RadioButton twoSailsRadio;

    private Vehicle boat = null;

    private Delegate eventAddBoat;

    private static final DataFormat drawingFormat = new DataFormat("DrawingFormat");

    private static final DataFormat colorFormat = new DataFormat("ColorFormat");

    private void DrawBoat() {
        if (boat != null) {
            GraphicsContext gr = canvasBoat.getGraphicsContext2D();
            gr.clearRect(0, 0, canvasBoat.getWidth(), canvasBoat.getHeight());
            boat.SetPosition(40, 40, (int) canvasBoat.getWidth(), (int) canvasBoat.getHeight());
            boat.DrawTransport(gr);
        }
    }

    public void AddEvent(AddBoatListener listener) {
        if (eventAddBoat == null) {
            eventAddBoat = new Delegate(listener);
        } else {
            eventAddBoat.addListener(listener);
        }
    }

    private Color getPaneBackgroundColor(Pane panel) {
        String property = panel.styleProperty().getValue();
        return Color.valueOf(property.substring(property.indexOf(":") + 2, property.indexOf(';')));
    }

    @FXML
    void labelBoatHandleDragDetection(MouseEvent event) {
        Dragboard db = labelBoat.startDragAndDrop(TransferMode.ANY);
        ClipboardContent cb = new ClipboardContent();
        cb.putString(labelBoat.getText());
        db.setContent(cb);
        event.consume();
    }

    @FXML
    void labelSailsHandleDragDetection(MouseEvent event) {
        Label senderLabel = (Label) event.getSource();
        Dragboard db = senderLabel.startDragAndDrop(TransferMode.ANY);
        ClipboardContent cb = new ClipboardContent();
        IDrawing drawing = new DrawingDiamondSails();
        switch (senderLabel.getText()) {
            case "Triangle":
                drawing = new DrawingTriangleSails();
                break;
            case "Ellipse":
                drawing = new DrawingEllipseSails();
                break;
        }
        int sailsNum = 1;
        if (twoSailsRadio.isSelected()) {
            sailsNum = 2;
        }
        drawing.setNum(sailsNum);
        cb.put(drawingFormat, drawing);
        db.setContent(cb);
        event.consume();
    }

    @FXML
    void labelCatamaranHandleDragDetection(MouseEvent event) {
        Dragboard db = labelCatamaran.startDragAndDrop(TransferMode.ANY);
        ClipboardContent cb = new ClipboardContent();
        cb.putString(labelCatamaran.getText());
        db.setContent(cb);
        event.consume();
    }

    @FXML
    void canvasBoatHandleDragOver(DragEvent event) {
        if (event.getDragboard().hasString() || event.getDragboard().hasContent(drawingFormat)) {
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    @FXML
    void canvasBoatHandleDrop(DragEvent event) {
        IDrawing drawing = (IDrawing) event.getDragboard().getContent(drawingFormat);
        if (drawing != null && boat != null) {
            if (boat instanceof Catamaran) {
                Catamaran catamaran = (Catamaran) boat;
                catamaran.setDrawingSails(drawing);
            }
        } else {
            int sailsNum = 1;
            if (twoSailsRadio.isSelected()) {
                sailsNum = 2;
            }
            switch (event.getDragboard().getString()) {
                case "Boat":
                    boat = new Boat(spinnerSpeed.getValue(), spinnerWeight.getValue(), Color.GRAY);
                    break;
                case "Catamaran":
                    boat = new Catamaran(spinnerSpeed.getValue(), spinnerWeight.getValue(),
                            Color.GRAY, Color.BLACK, checkBoxPasSets.isSelected(), checkBoxBobs.isSelected(), checkBoxSails.isSelected(), sailsNum, SailForm.DIAMOND);
                    break;
            }
        }
        DrawBoat();
    }

    @FXML
    void labelColorHandleDragOver(DragEvent event) {
        if (event.getDragboard().hasContent(colorFormat)) {
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    @FXML
    void labelMainColorHandleDrop(DragEvent event) {
        Color color = Color.valueOf((String) event.getDragboard().getContent(colorFormat));
        if (boat != null) {
            boat.setMainColor(color);
            DrawBoat();
        }
    }

    @FXML
    void labelDopColorHandleDrop(DragEvent event) {
        Color color = Color.valueOf((String) event.getDragboard().getContent(colorFormat));
        if (boat != null) {
            if (boat instanceof Catamaran) {
                var catamaran = (Catamaran) boat;
                catamaran.SetDopColor(color);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Boat don't have dop color");
                alert.showAndWait();
            }
            DrawBoat();
        }
    }

    @FXML
    void buttonAddClick() {
        if (eventAddBoat != null) {
            eventAddBoat.invoke(boat);
        }
        Stage stage = (Stage) buttonCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void initialize() {
        spinnerWeight.setEditable(true);
        spinnerSpeed.setEditable(true);
        SpinnerValueFactory<Integer> factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(100, 1000, 100);
        spinnerSpeed.setValueFactory(factory);
        spinnerWeight.setValueFactory(factory);
        EventHandler<MouseEvent> handlerDragDetection = mouseEvent -> {
            Pane pane = (Pane) mouseEvent.getSource();
            Dragboard db = pane.startDragAndDrop(TransferMode.ANY);
            ClipboardContent cb = new ClipboardContent();
            cb.put(colorFormat, getPaneBackgroundColor(pane).toString());
            db.setContent(cb);
            mouseEvent.consume();
        };
        panelRed.addEventFilter(MouseEvent.DRAG_DETECTED, handlerDragDetection);
        panelBlack.addEventFilter(MouseEvent.DRAG_DETECTED, handlerDragDetection);
        panelBlue.addEventFilter(MouseEvent.DRAG_DETECTED, handlerDragDetection);
        panelFuchsia.addEventFilter(MouseEvent.DRAG_DETECTED, handlerDragDetection);
        panelGray.addEventFilter(MouseEvent.DRAG_DETECTED, handlerDragDetection);
        panelLime.addEventFilter(MouseEvent.DRAG_DETECTED, handlerDragDetection);
        panelYellow.addEventFilter(MouseEvent.DRAG_DETECTED, handlerDragDetection);
        panelWhite.addEventFilter(MouseEvent.DRAG_DETECTED, handlerDragDetection);
        buttonCancel.setOnAction(e -> {
            Stage stage = (Stage) buttonCancel.getScene().getWindow();
            stage.close();
        });
    }
}
