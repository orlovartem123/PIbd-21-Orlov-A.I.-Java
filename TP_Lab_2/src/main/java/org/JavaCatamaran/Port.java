package org.JavaCatamaran;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Port<T extends ITransport, T1 extends IDrawing> implements Iterable<T> {

    private final List<T> _places;

    private final int _maxCount;

    private final int pictureWidth;

    private final int pictureHeight;

    private final int _placeSizeWidth = 240;

    private final int _placeSizeHeight = 110;

    private final int parkingPlacesInRow;

    public Port(int picWidth, int picHeight) {
        int width = picWidth / _placeSizeWidth;
        int height = picHeight / _placeSizeHeight;
        _maxCount = width * height;
        _places = new LinkedList<>();
        pictureWidth = picWidth;
        pictureHeight = picHeight;
        parkingPlacesInRow = height;
    }

    public T getObject(int index) {
        if (index < -1 || index >= _places.size()) {
            return null;
        }
        return _places.get(index);
    }

    public boolean append(T boat) throws PortOverflowException, PortAlreadyHaveException {
        if (_places.size() >= _maxCount) {
            throw new PortOverflowException();
        }
        if (_places.contains(boat)) {
            throw new PortAlreadyHaveException();
        }
        _places.add(boat);
        return true;
    }

    public T takeObject(int index) throws PortNotFoundException {
        if (index < -1 || index >= _places.size()) {
            throw new PortNotFoundException(index);
        }
        T boat = _places.get(index);
        _places.remove(index);
        return boat;
    }

    public void clearList() {
        _places.clear();
    }

    public boolean isBiggerThan(Port<T, T1> port) {
        return _places.size() > port._places.size();
    }

    public boolean isSmallerThan(Port<T, T1> port) {
        return _places.size() < port._places.size();
    }

    public void Draw(GraphicsContext gc) {
        DrawMarking(gc);
        for (int i = 0; i < _places.size(); ++i) {
            int x = (i / parkingPlacesInRow) * _placeSizeWidth;
            int y = (i - parkingPlacesInRow * (i / parkingPlacesInRow)) * _placeSizeHeight;
            _places.get(i).SetPosition(x + 5, y + 5, pictureWidth, pictureHeight);
            _places.get(i).DrawTransport(gc);
        }
    }

    private void DrawMarking(GraphicsContext gc) {
        gc.setStroke(Color.BLACK);
        for (int i = 0; i < pictureWidth / _placeSizeWidth; i++) {
            for (int j = 0; j < pictureHeight / _placeSizeHeight + 1; ++j) {//линия рамзетки места
                gc.strokeLine(i * _placeSizeWidth, j * _placeSizeHeight, i * _placeSizeWidth + _placeSizeWidth / 2, j * _placeSizeHeight);
            }
            gc.strokeLine(i * _placeSizeWidth, 0, i * _placeSizeWidth, (pictureHeight / _placeSizeHeight) * _placeSizeHeight);
        }
    }

    public T GetNext(int index) {
        if (index < 0 || index >= _places.size()) {
            return null;
        }
        return _places.get(index);
    }

    public void sort() {
        _places.sort((Comparator<T>) new BoatComparer());
    }

    @Override
    public Iterator<T> iterator() {
        return new PortIterator();

    }

    private class PortIterator implements Iterator<T> {

        private int index;

        @Override
        public boolean hasNext() {
            return index < _places.size();
        }

        @Override
        public T next() {
            return _places.get(index++);
        }
    }
}
