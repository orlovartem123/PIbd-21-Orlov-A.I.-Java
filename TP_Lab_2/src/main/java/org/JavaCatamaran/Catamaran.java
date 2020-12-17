package org.JavaCatamaran;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Catamaran extends Boat {

    private IDrawing drawingSails;

    public void setDrawingSails(IDrawing drawing) {
        drawingSails = drawing;
    }

    public Color DopColor;

    private boolean PassangerSeat;

    private boolean Bobs;

    private boolean Sails;

    public Catamaran(int maxSpeed, float weight, Color mainColor, Color dopColor,
                     boolean seats, boolean bobs, boolean sails, int sailsNum, SailForm sailForm) {
        super(maxSpeed, weight, mainColor, 104, 100);
        DopColor = dopColor;
        PassangerSeat = seats;
        Bobs = bobs;
        Sails = sails;
        switch (sailForm) {
            case ELLIPSE:
                drawingSails = new DrawingEllipseSails();
                break;
            case DIAMOND:
                drawingSails = new DrawingDiamondSails();
                break;
            case TRIANGLE:
                drawingSails = new DrawingTriangleSails();
                break;
        }
        drawingSails.setNum(sailsNum);
    }

    public Catamaran(String info) {
        super(info);
        String[] strings = info.split(separator);
        if (strings.length == 9) {
            MaxSpeed = Integer.parseInt(strings[0]);
            Weight = Float.parseFloat(strings[1]);
            MainColor = Color.valueOf(strings[2]);
            DopColor = Color.valueOf(strings[3]);
            PassangerSeat = Boolean.parseBoolean(strings[4]);
            Bobs = Boolean.parseBoolean(strings[5]);
            Sails = Boolean.parseBoolean(strings[6]);
            drawingSails = new DrawingDiamondSails();
            switch (strings[7]) {
                case "ELLIPSE":
                    drawingSails = new DrawingEllipseSails();
                    break;
                case "DIAMOND":
                    drawingSails = new DrawingDiamondSails();
                    break;
                case "TRIANGLE":
                    drawingSails = new DrawingTriangleSails();
                    break;
            }
            drawingSails.setNum(Integer.parseInt(strings[8]));
        }
    }

    @Override
    public void DrawTransport(GraphicsContext gc) {
        super.DrawTransport(gc);
        if (Bobs) {
            gc.fillRect(_startPosX + 60, _startPosY + 16, 28, 50);
            gc.fillRect(_startPosX + 88, _startPosY, 16, 80);
            double[] xpointsTB = {_startPosX + 88, _startPosX + 96, _startPosX + 104};
            double[] ypointsTB = {_startPosY + 80, _startPosY + 100, _startPosY + 80};
            gc.fillPolygon(xpointsTB, ypointsTB, xpointsTB.length);
            gc.fillRect(_startPosX, _startPosY, 16, 80);
            double[] xpointsFB = {_startPosX, _startPosX + 8, _startPosX + 16};
            double[] ypointsFB = {_startPosY + 80, _startPosY + 100, _startPosY + 80};
            gc.fillPolygon(xpointsFB, ypointsFB, xpointsFB.length);
            gc.fillRect(_startPosX + 16, _startPosY + 16, 28, 50);
        }
        if (PassangerSeat) {
            gc.setFill(DopColor);
            gc.fillRect(_startPosX + 46, _startPosY + 40, 12, 18);
        }
        if (Sails) {
            drawingSails.drawing(gc, Color.WHITESMOKE, _startPosX, _startPosY);
        }
    }

    public void SetDopColor(Color color) {
        DopColor = color;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("");
        result.append(super.toString());
        result.append(separator);
        result.append(DopColor.toString());
        result.append(separator);
        result.append(PassangerSeat);
        result.append(separator);
        result.append(Bobs);
        result.append(separator);
        result.append(Sails);
        result.append(separator);
        result.append(drawingSails.getSailForm());
        result.append(separator);
        result.append(drawingSails.getNum());
        return result.toString();
    }

}
