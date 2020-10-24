package JavaCatamaran;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Catamaran extends Boat {

    private IDrawing drawingBobs;

    private IDrawing drawingSails;

    public void setMaxSpeed(int speed) {
        MaxSpeed = speed;
    }

    public int getMaxSpeed() {
        return MaxSpeed;
    }

    public Color DopColor;

    private boolean PassangerSeat;

    public Catamaran(int maxSpeed, float weight, Color mainColor, Color dopColor,
                     boolean seats, int bobsNum, SailForm sailForm) {
        super(maxSpeed, weight, mainColor, 104, 100);
        DopColor = dopColor;
        PassangerSeat = seats;
        drawingBobs = new DrawingBobs();
        drawingBobs.setNum(bobsNum);
        if (sailForm == SailForm.TRIANGLE) {
            drawingSails = new DrawingTriangleSails();
        } else {
            drawingSails = new DrawingDiamondSails();
        }
        drawingSails.setNum(bobsNum);
    }

    @Override
    public void DrawTransport(GraphicsContext gc) {
        gc.clearRect(0, 0, _pictureWidth, _pictureHeight);
        super.DrawTransport(gc);
        drawingBobs.drawing(gc, MainColor, _startPosX, _startPosY);
        if (PassangerSeat) {
            gc.setFill(DopColor);
            gc.fillRect(_startPosX + 46, _startPosY + 20, 12, 20);
        }
        drawingSails.drawing(gc, Color.WHITESMOKE, _startPosX, _startPosY);
    }
}
