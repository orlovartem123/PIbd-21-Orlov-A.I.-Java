package JavaCatamaran;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.Serializable;

public class DrawingTriangleSails implements IDrawing, Serializable {

    private int sailNum = 1;

    public void setNum(int num) {
        if (num == 1) {
            sailNum = 1;
        }
        if (num == 2) {
            sailNum = 2;
        }
    }

    public void drawing(GraphicsContext gc, Color mainColor, float _startPosX, float _startPosY) {
        gc.setFill(mainColor);
        switch (sailNum) {
            case 2:
                double[] xpointsUB = {_startPosX + 52, _startPosX + 46, _startPosX + 58};
                double[] ypointsUB = {_startPosY + 35, _startPosY + 20, _startPosY + 20};
                gc.fillPolygon(xpointsUB, ypointsUB, xpointsUB.length);
            case 1:
                double[] xpointsDB = {_startPosX + 52, _startPosX + 46, _startPosX + 58};
                double[] ypointsDB = {_startPosY + 80, _startPosY + 65, _startPosY + 65};
                gc.fillPolygon(xpointsDB, ypointsDB, xpointsDB.length);

        }
    }
}
