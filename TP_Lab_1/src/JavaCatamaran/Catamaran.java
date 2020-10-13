package JavaCatamaran;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Catamaran {

    private DrowingBobs drawingBobs;
    private float _startPosX;
    private float _startPosY;
    private int _pictureWidth;
    private int _pictureHeight;
    private final int catamaranWidth = 104;  //60
    private final int catamaranHeight = 100;  //100
    private int MaxSpeed;

    public Catamaran(int maxSpeed, float weight, Color mainColor, Color dopColor,
                     boolean seats, boolean bobs, int bobsNum) {
        drawingBobs = new DrowingBobs();
        drawingBobs.setBobsNum(bobsNum);
        MaxSpeed = maxSpeed;
        Weight = weight;
        MainColor = mainColor;
        DopColor = dopColor;
        PassangerSeat = seats;
        Bobs = bobs;

    }

    public void setMaxSpeed(int speed) {
        MaxSpeed = speed;
    }

    public int getMaxSpeed() {
        return MaxSpeed;
    }


    private float Weight;

    public void setWeight(float weight) {
        Weight = weight;
    }

    public float getWeight() {
        return MaxSpeed;
    }


    private Color MainColor;

    public void setMainColor(Color color) {
        MainColor = color;
    }

    public Color getMainColor() {
        return MainColor;
    }

    public Color DopColor;

    public void setDopColor(Color color) {
        DopColor = color;
    }

    public Color getDopColor() {
        return DopColor;
    }

    private boolean PassangerSeat;

    public void setPassangerSeat(boolean passangerSeat) {
        PassangerSeat = passangerSeat;
    }

    public boolean getPassangerSeat() {
        return PassangerSeat;
    }

    private boolean Bobs;

    public void setBobs(boolean bobs) {
        Bobs = bobs;
    }

    public boolean getBobs() {
        return Bobs;
    }


    public void SetPosition(int x, int y, int width, int height) {
        _startPosX = x;
        _startPosY = y;
        _pictureWidth = width;
        _pictureHeight = height;
    }

    public void MoveTransport(Direction direction) {
        float step = MaxSpeed * 100 / Weight;
        switch (direction) {
            // вправо
            case Right:
                if (_startPosX + step < _pictureWidth - catamaranWidth) {
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
                if (_startPosY + step < _pictureHeight - catamaranHeight) {
                    _startPosY += step;
                }
                break;
        }
    }


    public void DrawTransport(GraphicsContext gc) {
        gc.clearRect(0, 0, _pictureWidth, _pictureHeight);
        drawingBobs.drawBobs(gc, MainColor, _startPosX, _startPosY);
        if (PassangerSeat) {
            gc.setFill(DopColor);
            switch (drawingBobs.getBobsEum()) {
                case THREE:
                    gc.fillRect(_startPosX + 90, _startPosY + 20, 12, 20);
                case TWO:
                    gc.fillRect(_startPosX + 2, _startPosY + 20, 12, 20);
                case ONE:
                    gc.fillRect(_startPosX + 46, _startPosY + 20, 12, 20);
            }
        }
    }

}
