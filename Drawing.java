package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Drawing {
    public static void drawGrid(Canvas canvas, int[][] grid, int width, int height) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grid[i][j] == 1) {
                    gc.setFill(Color.BLUE);
                    gc.fillRect((canvas.getWidth() * j) / width, (canvas.getHeight() * i) / height,
                            canvas.getWidth() / width, canvas.getHeight() / height);
                } else {
                    gc.setFill(Color.RED);
                    gc.fillRect((canvas.getWidth() * j) / width, (canvas.getHeight() * i) / height,
                            canvas.getWidth() / width, canvas.getHeight() / height);
                }
            }
        }
    }
}
