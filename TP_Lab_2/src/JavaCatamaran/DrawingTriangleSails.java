package JavaCatamaran;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawingTriangleSails implements IDrawing {

    private BobsEnum bobsEnum = BobsEnum.ONE;

    public void setNum(int bobsNum) {
        if (bobsNum == 2) {
            this.bobsEnum = BobsEnum.TWO;
        }
        if (bobsNum == 3) {
            this.bobsEnum = BobsEnum.THREE;
        }
    }

    public void drawing(GraphicsContext gc, Color mainColor, float _startPosX, float _startPosY) {
        gc.setFill(mainColor);
        switch (bobsEnum) {
            case THREE:
                double[] xpointsTB = {_startPosX + 96, _startPosX + 90, _startPosX + 102};
                double[] ypointsTB = {_startPosY + 75, _startPosY + 60, _startPosY + 60};
                gc.fillPolygon(xpointsTB, ypointsTB, xpointsTB.length);
            case TWO:
                double[] xpointsFB = {_startPosX + 8, _startPosX + 2, _startPosX + 14};
                double[] ypointsFB = {_startPosY + 75, _startPosY + 60, _startPosY + 60};
                gc.fillPolygon(xpointsFB, ypointsFB, xpointsFB.length);
            case ONE:
                double[] xpointsSB = {_startPosX + 52, _startPosX + 46, _startPosX + 58};
                double[] ypointsSB = {_startPosY + 75, _startPosY + 60, _startPosY + 60};
                gc.fillPolygon(xpointsSB, ypointsSB, xpointsSB.length);
        }
    }
}
