package org.JavaCatamaran;

import javafx.scene.canvas.GraphicsContext;

public interface ITransport {

    void SetPosition(int x, int y, int width, int height);

    void MoveTransport(Direction direction);

    void DrawTransport(GraphicsContext gc);
}
