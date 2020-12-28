package org.JavaCatamaran;

import javafx.scene.paint.Color;

import java.util.Comparator;

public class BoatComparer implements Comparator<Vehicle> {

    private int compareColors(Color x, Color y) {
        if (x.getRed() != y.getRed()) {
            return (int) (x.getRed() - y.getRed());
        }
        if (x.getGreen() != y.getGreen()) {
            return (int) (x.getGreen() - y.getGreen());
        }
        return (int) (x.getBlue() - y.getBlue());
    }

    public int compare(Vehicle x, Vehicle y) {
        if (x instanceof Catamaran && y instanceof Catamaran) {
            return ComparerCatamaran((Catamaran) x, (Catamaran) y);
        }
        if (x instanceof Catamaran && y instanceof Boat) {
            return 1;
        }
        if (x instanceof Boat && y instanceof Catamaran) {
            return -1;
        }
        if (x instanceof Boat && y instanceof Boat) {
            return ComparerBoat((Boat) x, (Boat) y);
        }
        return 0;
    }

    public int ComparerBoat(Boat x, Boat y) {
        if (x.MaxSpeed != y.MaxSpeed) {
            return Integer.compare(x.MaxSpeed, y.MaxSpeed);
        }
        if (x.Weight != y.Weight) {
            return Float.compare(x.Weight, y.Weight);
        }
        if (x.MainColor != y.MainColor) {
            return compareColors(x.MainColor, y.MainColor);
        }
        return 0;
    }

    private int ComparerCatamaran(Catamaran x, Catamaran y) {
        var res = ComparerBoat(x, y);
        if (res != 0) {
            return res;
        }
        if (x.DopColor != y.DopColor) {
            return compareColors(x.DopColor, y.DopColor);
        }
        if (x.isPassangerSeat() != y.isPassangerSeat()) {
            return Boolean.compare(x.isPassangerSeat(), y.isPassangerSeat());
        }
        if (x.isBobs() != y.isBobs()) {
            return Boolean.compare(x.isBobs(), y.isBobs());
        }
        if (x.isSails() != y.isSails()) {
            return Boolean.compare(x.isSails(), y.isSails());
        }
        if (x.getDrawingSails().getNum() != y.getDrawingSails().getNum()) {
            return Integer.compare(x.getDrawingSails().getNum(), y.getDrawingSails().getNum());
        }
        return 0;
    }
}
