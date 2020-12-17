package org.JavaCatamaran;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Boat extends Vehicle {

    private int boatWidth = 104;//104

    private int boatHeight = 100;//100

    protected final String separator = ";";

    public Boat(int maxSpeed, float weight, Color mainColor) {
        MaxSpeed = maxSpeed;
        Weight = weight;
        MainColor = mainColor;
    }

    protected Boat(int maxSpeed, float weight, Color mainColor, int boatWidth, int
            boatHeight) {
        MaxSpeed = maxSpeed;
        Weight = weight;
        MainColor = mainColor;
        this.boatWidth = boatWidth;
        this.boatHeight = boatHeight;
    }

    public Boat(String info) {
        String[] strings = info.split(separator);
        if (strings.length == 3) {
            MaxSpeed = Integer.parseInt(strings[0]);
            Weight = Float.parseFloat(strings[1]);
            MainColor = Color.valueOf(strings[2]);
        }
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
        gc.setFill(MainColor);
        gc.fillRect(_startPosX + 44, _startPosY, 16, 80);
        double[] xpointsSB = {_startPosX + 44, _startPosX + 52, _startPosX + 60};
        double[] ypointsSB = {_startPosY + 80, _startPosY + 100, _startPosY + 80};
        gc.fillPolygon(xpointsSB, ypointsSB, xpointsSB.length);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("");
        result.append(MaxSpeed);
        result.append(separator);
        result.append(Weight);
        result.append(separator);
        result.append(MainColor.toString());
        return result.toString();
    }
}
