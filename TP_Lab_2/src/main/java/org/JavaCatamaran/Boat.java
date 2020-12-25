package org.JavaCatamaran;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class Boat extends Vehicle implements Comparable<Boat>, Iterator, Iterable {

    private List<Object> listProperties = new LinkedList<>();

    private int boatWidth = 104;//104

    public int getBoatWidth() {
        return boatWidth;
    }

    private int boatHeight = 100;//100

    public int getBoatHeight() {
        return boatHeight;
    }

    protected final String separator = ";";

    protected int currentIndex = -1;

    public Boat(int maxSpeed, float weight, Color mainColor) {
        MaxSpeed = maxSpeed;
        Weight = weight;
        MainColor = mainColor;
        listProperties.add(MaxSpeed);
        listProperties.add(Weight);
        listProperties.add(MainColor);
        listProperties.add(this.boatHeight);
        listProperties.add(this.boatWidth);
    }

    protected Boat(int maxSpeed, float weight, Color mainColor, int boatWidth, int
            boatHeight) {
        MaxSpeed = maxSpeed;
        Weight = weight;
        MainColor = mainColor;
        this.boatWidth = boatWidth;
        this.boatHeight = boatHeight;
        listProperties.add(MaxSpeed);
        listProperties.add(Weight);
        listProperties.add(MainColor);
        listProperties.add(this.boatHeight);
        listProperties.add(this.boatWidth);
    }

    public Boat(String info) {
        String[] strings = info.split(separator);
        if (strings.length == 3) {
            MaxSpeed = Integer.parseInt(strings[0]);
            Weight = Float.parseFloat(strings[1]);
            MainColor = Color.valueOf(strings[2]);
        }
        listProperties.add(MaxSpeed);
        listProperties.add(Weight);
        listProperties.add(MainColor);
        listProperties.add(this.boatHeight);
        listProperties.add(this.boatWidth);
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

    public boolean equals(Boat other) {
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
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Boat)) {
            return false;
        } else {
            return equals((Boat) obj);
        }
    }

    @Override
    public int compareTo(Boat other) {
        if (this.MaxSpeed != other.MaxSpeed) {
            return this.MaxSpeed - other.MaxSpeed;
        }
        if (this.Weight != other.Weight) {
            return (int) (this.Weight - other.Weight);
        }
        if (this.MainColor.getRed() != other.MainColor.getRed()) {
            return (int) (this.MainColor.getRed() - other.MainColor.getRed());
        }
        if (this.MainColor.getGreen() != other.MainColor.getGreen()) {
            return (int) (this.MainColor.getGreen() - other.MainColor.getGreen());
        }
        if (this.MainColor.getBlue() != other.MainColor.getBlue()) {
            return (int) (this.MainColor.getBlue() - other.MainColor.getBlue());
        }
        return 0;
    }

    @Override
    public Iterator iterator() {
        currentIndex = -1;
        return this;
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
