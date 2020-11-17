package JavaCatamaran;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Port<T extends ITransport, T1 extends IDrawing> {

    private final ArrayList<T> _places;

    private final int pictureWidth;

    private final int pictureHeight;

    private final int _placeSizeWidth = 240;

    private final int _placeSizeHeight = 110;

    private final int parkingPlacesInRow;

    public Port(int picWidth, int picHeight) {
        int width = picWidth / _placeSizeWidth;
        int height = picHeight / _placeSizeHeight;
        _places = new ArrayList<>(width * height);
        for (int i = 0; i < width * height; i++) {
            _places.add(null);
        }
        pictureWidth = picWidth;
        pictureHeight = picHeight;
        parkingPlacesInRow = height;
    }

    public boolean append(T boat) {
        for (int i = 0; i < _places.size(); i++) {
            if (_places.get(i) == null) {
                _places.remove(i);
                _places.add(i, boat);
                int x = (i / parkingPlacesInRow) * _placeSizeWidth;
                int y = (i - parkingPlacesInRow * (i / parkingPlacesInRow)) * _placeSizeHeight;
                boat.SetPosition(x + 5, y + 5, pictureWidth, pictureHeight);
                return true;
            }
        }
        return false;
    }

    public T takeObject(int index) {
        if (index >= _places.size() || index < 0) {
            return null;
        }
        T boat = _places.get(index);
        _places.remove(index);
        _places.add(index, null);
        return boat;
    }

    public boolean isBiggerThan(Port<T, T1> port) {
        return _places.size() > port._places.size();
    }

    public boolean isSmallerThan(Port<T, T1> port) {
        return _places.size() < port._places.size();
    }

    public void Draw(GraphicsContext gc) {
        DrawMarking(gc);
        for (T place : _places) {
            if (place != null) {
                place.DrawTransport(gc);
            }
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
}
