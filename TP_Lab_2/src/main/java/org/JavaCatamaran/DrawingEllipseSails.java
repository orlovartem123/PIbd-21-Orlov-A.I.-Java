package org.JavaCatamaran;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.Serializable;

public class DrawingEllipseSails implements IDrawing, Serializable {

    private int sailNum = 1;

    public void setNum(int num) {
        if (num == 1) {
            sailNum = 1;
        }
        if (num == 2) {
            sailNum = 2;
        }
    }

    public int getNum() {
        return sailNum;
    }

    public SailForm getSailForm() {
        return SailForm.ELLIPSE;
    }

    public void drawing(GraphicsContext gc, Color mainColor, float _startPosX, float _startPosY) {
        gc.setFill(mainColor);
        switch (sailNum) {
            case 2:
                gc.fillOval(_startPosX + 46, _startPosY + 20, 12, 15);
            case 1:
                gc.fillOval(_startPosX + 46, _startPosY + 70, 12, 15);
        }
    }

}
