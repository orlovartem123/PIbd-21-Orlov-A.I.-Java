package JavaCatamaran;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Vehicle implements ITransport {

    protected float _startPosX;

    protected float _startPosY;

    protected int _pictureWidth;

    protected int _pictureHeight;

    public int MaxSpeed;

    protected void setMaxSpeed(int speed){
        MaxSpeed=speed;
    }

    public int getMaxSpeed(){
        return MaxSpeed;
    }

    public float Weight;

    protected void setWeight(float weight){
        Weight=weight;
    }

    public float getWeight(){
        return Weight;
    }

    public Color MainColor;

    protected void setMainColor(Color color){
        MainColor=color;
    }

    public Color getMainColor(){
        return MainColor;
    }


    public void SetPosition(int x, int y, int width, int height){
        _startPosX = x;
        _startPosY = y;
        _pictureWidth = width;
        _pictureHeight = height;
    }

    public abstract void MoveTransport(Direction direction);

    public abstract void DrawTransport(GraphicsContext gc);
}
