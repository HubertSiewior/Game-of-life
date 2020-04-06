package sample;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Calculations {
    private int[][] grid;
    private int width;
    private int height;

    public Calculations(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new int[this.height][this.width];
    }

    public void setRandomGrid() {
        Random generator = new Random();
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                int x = generator.nextInt(10);
                if (x % 2==0) {
                    this.grid[i][j] = 1;
                }
            }
        }
    }

    public void setGliderDrid() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                if((i==(height-1)/2-1)&&(j==(width-1)/2-1)){
                   grid[i-1][j-1]=1; grid[i-1][j]=1; grid[i-1][j+1]=1;
                    grid[i][j-1]=1;grid[i+1][j]=1;
                }
            }
        }
    }

    public void setOscillatorGrid(){
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                if((i==height/2-1)&&(j==width/2-1)){
                    grid[i-1][j]=1;grid[i][j]=1;grid[i+1][j]=1;
                }
            }
        }
    }
    public void setBeehiveGrid(){
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                if((i==height/2-1)&&(j==width/2-1)){
                    grid[i-1][j]=1; grid[i-1][j+1]=1;grid[i][j+2]=1;
                    grid[i][j-1]=1; grid[i+1][j]=1;grid[i+1][j+1]=1;
                }
            }
        }
    }

    public int[][] getGrid() {
        return grid;
    }

    public int[][] getNewGridWithPBC(int[][] oldGrid) {
        int[][] newGrid = new int[this.height][this.width];
        //int[][] newGrid = oldGrid;
        int sum=0;
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                sum=0;
                if (i == 0 && j == 0) {
                    sum+=oldGrid[i][j + 1]+oldGrid[i + 1][j]+oldGrid[i + 1][j + 1]+oldGrid[i+1][this.width-1]+oldGrid[i][this.width-1]+oldGrid[height-1][width- 1]+oldGrid[i][width-1]+oldGrid[i+1][width-1];
                } else if (i == 0 && j == (this.width - 1)) {
                    sum+=oldGrid[i][j - 1]+oldGrid[i + 1][j - 1]+oldGrid[i + 1][j]+oldGrid[height-1][j-1]+oldGrid[height-1][j]+oldGrid[height-1][0]+oldGrid[0][0]+oldGrid[1][0];
                } else if (i == 0 && j!=0 && j!=(width-1)) {
                    sum+=oldGrid[i][j - 1]+oldGrid[i][j + 1]+oldGrid[i + 1][j - 1]+oldGrid[i + 1][j]+oldGrid[i + 1][j + 1]+oldGrid[height-1][j]+oldGrid[height-1][j-1]+oldGrid[height-1][j+1];
                } else if (i == (this.height - 1) && j == 0) {
                    sum+=oldGrid[i - 1][j]+oldGrid[i - 1][j + 1]+oldGrid[i][j + 1]+oldGrid[0][j + 1]+oldGrid[0][j]+oldGrid[0][width-1]+oldGrid[i][width-1]+oldGrid[i-1][width-1];
                } else if (j == 0&& i!=0 && i!=(height-1)) {
                    sum+=oldGrid[i - 1][j]+oldGrid[i - 1][j + 1]+oldGrid[i][j + 1]+oldGrid[i + 1][j + 1]+oldGrid[i + 1][j]+oldGrid[i+1][width-1]+oldGrid[i][width-1]+oldGrid[i-1][width-1];
                } else if (i == (this.height - 1) && j == (this.width - 1)) {
                    continue;
                } else if (i == (this.height - 1)&& j!=0 && j!=(width-1)) {
                    sum+=oldGrid[i][j - 1]+oldGrid[i - 1][j - 1]+oldGrid[i - 1][j]+oldGrid[i - 1][j + 1]+oldGrid[i][j + 1]+oldGrid[0][j + 1]+oldGrid[0][j ]+oldGrid[0][j - 1];
                } else if (j == (this.width - 1)&& i!=0 && i!=(height-1)) {
                    sum+=oldGrid[i - 1][j]+oldGrid[i - 1][j - 1]+oldGrid[i][j - 1]+oldGrid[i + 1][j - 1]+oldGrid[i + 1][j]+oldGrid[i+1][0]+oldGrid[i][0]+oldGrid[i-1][0];
                } else {
                    sum+=oldGrid[i - 1][j - 1]+oldGrid[i - 1][j]+oldGrid[i - 1][j + 1]+oldGrid[i][j + 1]+oldGrid[i + 1][j + 1]+oldGrid[i + 1][j]+oldGrid[i + 1][j - 1]+oldGrid[i][j - 1];
                }
                newGrid[i][j] = rules(sum, oldGrid[i][j]);
            }
        }
        return newGrid;
    }

    public int[][] getNewGridWithNoPBC(int[][] oldGrid) {
       // int[][] newGrid = oldGrid;
        int[][] newGrid = new int[this.height][this.width];
        int sum=0;
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                sum=0;
                if (i == 0 && j == 0) {
                    sum+=oldGrid[i][j + 1]+oldGrid[i + 1][j]+oldGrid[i + 1][j + 1];
                } else if (i == 0 && j == (this.width - 1)) {
                    sum+=oldGrid[i][j - 1]+oldGrid[i + 1][j - 1]+oldGrid[i + 1][j];
                } else if (i == 0 && j!=0 && j!=(width-1)) {
                    sum+=oldGrid[i][j - 1]+oldGrid[i][j + 1]+oldGrid[i + 1][j - 1]+oldGrid[i + 1][j]+oldGrid[i + 1][j + 1];
                } else if (i == (this.height - 1) && j == 0) {
                    sum+=oldGrid[i - 1][j]+oldGrid[i - 1][j + 1]+oldGrid[i][j + 1];
                } else if (j == 0 && i!=0 && i!=(height-1)) {
                    sum+=oldGrid[i - 1][j]+oldGrid[i - 1][j + 1]+oldGrid[i][j + 1]+oldGrid[i + 1][j + 1]+oldGrid[i + 1][j];
                } else if (i == (this.height - 1) && j == (this.width - 1)) {
                    continue;
                } else if (i == (this.height - 1) && j!=0 && j!=(width-1)) {
                    sum+=oldGrid[i][j - 1]+oldGrid[i - 1][j - 1]+oldGrid[i - 1][j]+oldGrid[i - 1][j + 1]+oldGrid[i][j + 1];
                } else if (j == (this.width - 1)&& i!=0 && i!=(height-1)) {
                    sum+=oldGrid[i - 1][j]+oldGrid[i - 1][j - 1]+oldGrid[i][j - 1]+oldGrid[i + 1][j - 1]+oldGrid[i + 1][j];
                } else {
                    sum+=oldGrid[i - 1][j - 1]+oldGrid[i - 1][j]+oldGrid[i - 1][j + 1]+oldGrid[i][j + 1]+oldGrid[i + 1][j + 1]+oldGrid[i + 1][j]+oldGrid[i + 1][j - 1]+oldGrid[i][j - 1];
                }
                newGrid[i][j] = rules(sum, oldGrid[i][j]);
            }
        }
        return newGrid;
    }

    public int rules(int sum,int cell){
        if (cell == 0 &&sum==3) return 1;
        if (cell == 1 && sum == 2) return 1;
        if (cell == 1 && sum == 3) return 1;
        return 0;
    }


}
