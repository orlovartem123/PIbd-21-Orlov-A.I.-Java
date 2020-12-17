package org.JavaCatamaran;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public interface IDrawing {

    void setNum(int num);

    int getNum();

    SailForm getSailForm();

    void drawing(GraphicsContext gc, Color color, float _startPosX, float _startPosY);
}
