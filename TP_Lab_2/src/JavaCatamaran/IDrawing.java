package JavaCatamaran;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public interface IDrawing {

    void setNum(int num);

    void drawing(GraphicsContext gc, Color color, float _startPosX, float _startPosY);

}
