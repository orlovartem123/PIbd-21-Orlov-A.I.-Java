package JavaCatamaran;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Boat extends Vehicle {

    public final int boatWidth;//104

    public final int boatHeight;//100

    public Boat(int maxSpeed, float weight, Color mainColor) {
        MaxSpeed = maxSpeed;
        Weight = weight;
        MainColor = mainColor;
        boatWidth = 104;
        boatHeight = 100;
    }

    protected Boat(int maxSpeed, float weight, Color mainColor, int boatWidth, int
            boatHeight) {
        MaxSpeed = maxSpeed;
        Weight = weight;
        MainColor = mainColor;
        this.boatWidth = boatWidth;
        this.boatHeight = boatHeight;
    }

    public void MoveTransport(Direction direction) {
        float step = MaxSpeed * 100 / Weight;
        switch (direction) {
            // вправо
            case Right:
                if (_startPosX + step < _pictureWidth - boatWidth) {
                    _startPosX += step;
                }
                break;
            //влево
            case Left:
                if (_startPosX - step > 0) {
                    _startPosX -= step;
                }
                break;
            //вверх
            case Up:
                if (_startPosY - step > 0) {
                    _startPosY -= step;
                }
                break;
            //вниз
            case Down:
                if (_startPosY + step < _pictureHeight - boatHeight) {
                    _startPosY += step;
                }
                break;
        }
    }

    @Override
    public void DrawTransport(GraphicsContext gc) {
        gc.clearRect(0, 0, _pictureWidth, _pictureHeight);
        gc.setFill(MainColor);
        gc.fillRect(_startPosX + 44, _startPosY, 16, 80);
        double[] xpointsSB = {_startPosX + 44, _startPosX + 52, _startPosX + 60};
        double[] ypointsSB = {_startPosY + 80, _startPosY + 100, _startPosY + 80};
        gc.fillPolygon(xpointsSB, ypointsSB, xpointsSB.length);
    }

}
