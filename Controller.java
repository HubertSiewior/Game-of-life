package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class Controller implements Initializable {
    @FXML
    Button startButton;
    @FXML
    Canvas canvas;
    @FXML
    int[][] grid;
    @FXML
    TextField getWidth;
    @FXML
    TextField getHeight;
    @FXML
    TextField numberOfIterations;
    @FXML
    ComboBox<String> layout;
    @FXML
    Button next;
    @FXML
    Button pause;
    @FXML
    CheckBox PBC;
    @FXML
    CheckBox infinity;
    Calculations calculations;
    AnimationTimer animationTimer;
    int width;
    int height;
    int iterations;
    Boolean paused=false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        layout.setItems(FXCollections.observableArrayList("Beehive", "Glider", "Oscillator", "Random"));
        layout.setValue("Random");
        numberOfIterations.setText("100");
        getWidth.setText("50");
        getHeight.setText("50");
    }

    public void startGameButtom() {
        width = Integer.parseInt(getWidth.getText());
        height = Integer.parseInt(getHeight.getText());
        iterations = Integer.parseInt(numberOfIterations.getText());
        calculations = new Calculations(width, height);
        if(layout.getValue().equals("Random")) { calculations.setRandomGrid(); }
        else if(layout.getValue().equals("Beehive")) { calculations.setBeehiveGrid(); }
        else if(layout.getValue().equals("Glider")) { calculations.setGliderDrid(); }
        else if(layout.getValue().equals("Oscillator")){ calculations.setOscillatorGrid(); }
        this.grid = calculations.getGrid();
        Drawing.drawGrid(canvas, grid, width, height);
        if(PBC.isSelected()) grid=calculations.getNewGridWithPBC(grid);
        else grid = calculations.getNewGridWithNoPBC(grid);
    }

    public void pauseButton() {
        if(paused==false){
            try { animationTimer.stop();
                paused=true;
            } catch (NullPointerException nullException) {
                nullException.printStackTrace();
            }
        } else{
            try { animationTimer.start();
                paused=false;
            } catch (NullPointerException nullException) {
                nullException.printStackTrace();
            }
        }
    }

    public void nextButton() {
        final int[][][] grid = {this.grid};
        final int[] counter = {0};
        paused=false;
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                try {
                    if(infinity.isSelected()){ grid[0] = drawGrid(grid[0]); }
                    else {
                        if (counter[0] == iterations) animationTimer.stop();grid[0] = drawGrid(grid[0]);counter[0]++;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
               try { animationTimer.start();
        } catch (NullPointerException nullException) {
            nullException.printStackTrace();
        }
    }

    public int[][] drawGrid(int[][] grid) throws InterruptedException {
        Drawing.drawGrid(canvas,grid, width, height);
        if(PBC.isSelected()) grid=calculations.getNewGridWithPBC(grid);
        else grid = calculations.getNewGridWithNoPBC(grid);
        TimeUnit.MILLISECONDS.sleep(100);
        return grid;
    }

    public static void closeProgram() {
        Platform.exit();
        System.exit(0);
    }


}
