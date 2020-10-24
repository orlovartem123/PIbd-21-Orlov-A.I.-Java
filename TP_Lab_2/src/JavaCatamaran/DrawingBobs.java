package JavaCatamaran;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawingBobs implements IDrawing {

    private BobsEnum bobsNum = BobsEnum.ONE;

    public void setNum(int bobsNum) {
        if (bobsNum == 2) {
            this.bobsNum = BobsEnum.TWO;
        }
        if (bobsNum == 3) {
            this.bobsNum = BobsEnum.THREE;
        }
    }

    public void drawing(GraphicsContext gc, Color mainColor, float _startPosX, float _startPosY) {
        gc.setFill(mainColor);
        switch (bobsNum) {
            case THREE:
                gc.fillRect(_startPosX + 60, _startPosY + 16, 28, 50);
                gc.fillRect(_startPosX + 88, _startPosY, 16, 80);
                double[] xpointsTB = {_startPosX + 88, _startPosX + 96, _startPosX + 104};
                double[] ypointsTB = {_startPosY + 80, _startPosY + 100, _startPosY + 80};
                gc.fillPolygon(xpointsTB, ypointsTB, xpointsTB.length);
            case TWO:
                gc.fillRect(_startPosX, _startPosY, 16, 80);
                double[] xpointsFB = {_startPosX, _startPosX + 8, _startPosX + 16};
                double[] ypointsFB = {_startPosY + 80, _startPosY + 100, _startPosY + 80};
                gc.fillPolygon(xpointsFB, ypointsFB, xpointsFB.length);
                gc.fillRect(_startPosX + 16, _startPosY + 16, 28, 50);
        }
    }
}
