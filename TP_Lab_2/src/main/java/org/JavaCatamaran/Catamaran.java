package org.JavaCatamaran;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class Catamaran extends Boat implements Comparable<Boat> {

    private List<Object> listProperties = new LinkedList<>();

    private IDrawing drawingSails;

    public IDrawing getDrawingSails() {
        return drawingSails;
    }

    public void setDrawingSails(IDrawing drawing) {
        drawingSails = drawing;
        String sailForm = "";
        if (drawingSails instanceof DrawingDiamondSails) {
            sailForm = "DIAMOND";
        }
        if (drawingSails instanceof DrawingTriangleSails) {
            sailForm = "TRIANGLE";
        }
        if (drawingSails instanceof DrawingEllipseSails) {
            sailForm = "ELLIPSE";
        }
        listProperties.remove(listProperties.size() - 1);
        listProperties.add(sailForm);
    }

    public Color DopColor;

    private boolean PassangerSeat;

    public boolean isPassangerSeat() {
        return PassangerSeat;
    }

    private boolean Bobs;

    public boolean isBobs() {
        return Bobs;
    }

    private boolean Sails;

    public boolean isSails() {
        return Sails;
    }

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
        listProperties.add(MaxSpeed);
        listProperties.add(Weight);
        listProperties.add(MainColor);
        listProperties.add(super.getBoatHeight());
        listProperties.add(super.getBoatWidth());
        listProperties.add(DopColor);
        listProperties.add(PassangerSeat);
        listProperties.add(Bobs);
        listProperties.add(Sails);
        listProperties.add(sailsNum);
        listProperties.add(sailForm);
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
            listProperties.add(MaxSpeed);
            listProperties.add(Weight);
            listProperties.add(MainColor);
            listProperties.add(super.getBoatHeight());
            listProperties.add(super.getBoatWidth());
            listProperties.add(DopColor);
            listProperties.add(PassangerSeat);
            listProperties.add(Bobs);
            listProperties.add(Sails);
            listProperties.add(drawingSails.getNum());
            listProperties.add(strings[7]);
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

    public boolean equals(Catamaran other) {
        if (other == null) {
            return false;
        }
        if (!this.getClass().getName().equals(other.getClass().getName())) {
            return false;
        }
        if (MaxSpeed != other.MaxSpeed) {
            return false;
        }
        if (Weight != other.Weight) {
            return false;
        }
        if (!MainColor.equals(other.MainColor)) {
            return false;
        }
        if (!DopColor.equals(other.DopColor)) {
            return false;
        }
        if (Bobs != other.Bobs) {
            return false;
        }
        if (PassangerSeat != other.PassangerSeat) {
            return false;
        }
        if (Sails != other.Sails) {
            return false;
        }
        if (!drawingSails.getClass().getName().equals(other.drawingSails.getClass().getName())) {
            return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Catamaran)) {
            return false;
        } else {
            return equals((Catamaran) obj);
        }
    }

    private int compareColors(Color x, Color y) {
        if (x.getRed() != y.getRed()) {
            return (int) (x.getRed() - y.getRed());
        }
        if (x.getGreen() != y.getGreen()) {
            return (int) (x.getGreen() - y.getGreen());
        }
        return (int) (x.getBlue() - y.getBlue());
    }

    @Override
    public int compareTo(Boat other) {
        if (other instanceof Catamaran) {
            if (this.DopColor != ((Catamaran) other).DopColor) {
                return compareColors(this.DopColor, ((Catamaran) other).DopColor);
            }
            if (this.isPassangerSeat() != ((Catamaran) other).isPassangerSeat()) {
                return Boolean.compare(this.isPassangerSeat(), ((Catamaran) other).isPassangerSeat());
            }
            if (this.isBobs() != ((Catamaran) other).isBobs()) {
                return Boolean.compare(this.isBobs(), ((Catamaran) other).isBobs());
            }
            if (this.isSails() != ((Catamaran) other).isSails()) {
                return Boolean.compare(this.isSails(), ((Catamaran) other).isSails());
            }
            if (this.getDrawingSails().getNum() != ((Catamaran) other).getDrawingSails().getNum()) {
                return Integer.compare(this.getDrawingSails().getNum(), ((Catamaran) other).getDrawingSails().getNum());
            }
            return 0;
        }
        return super.compareTo(other);
    }

    @Override
    public boolean hasNext() {
        return currentIndex < (listProperties.size() - 1);
    }

    @Override
    public Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        currentIndex++;
        return listProperties.get(currentIndex);
    }
}
