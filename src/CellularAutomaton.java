import java.awt.*;
import java.util.Random;

public class CellularAutomaton extends Canvas implements Runnable {

    private Thread thread;
    private boolean running = false;

    private int[][] cells;
    private final int cellsWidth;
    private final int cellsHeight;

    private boolean[] survival = new boolean[8];
    private boolean[] birth = new boolean[8];

    public CellularAutomaton(int width, int height, int scale) {
        cellsWidth = width / scale;
        cellsHeight = height / scale;
        cells = new int[cellsWidth][cellsHeight];
        randomizeCells();
    }

    @Override
    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        // I think I should change this if I want a faster or slower speed
        double amountOfTicks = 30.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running) {
            long now = System.nanoTime();
            delta += (now-lastTime) / ns;
            lastTime = now;
            while(delta >= 1) {
                tick();
                delta--;
            }
            if(running) {
                render();
            }
            frames++;

            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    public void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
        render();
    }

    public void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void tick() {
        int[][] oldCells = clone2DArray(cells);
        int neighbors;
        for (int i = 0; i < cellsWidth; i++) {
            for (int j = 0; j < cellsHeight; j++) {
                neighbors = getNeighbors(i, j);
                if (oldCells[i][j] == 0 && birth[neighbors - 1]) {
                    cells[i][j] = 1;
                }
                if (oldCells[i][j] == 1 && !survival[neighbors - 1]) {
                    cells[i][j] = 0;
                }
            }
        }
    }

    private void render() {

    }

    public void randomizeCells() {
        Random random = new Random();

        for (int i = 0; i < cellsWidth; i++) {
            for (int j = 0; j < cellsHeight; j++) {
                cells[i][j] = random.nextInt(2);
            }
        }
    }

    // TODO: randomizeCell(double percentage) which allows for a percentage of alive cells to begin with

    private int getNeighbors(int x, int y) {
        int count = 0;
        for(int i = -1; i <= 1; i++) {
            for(int j = -1; j <= 1; j++) {
                int adjustX = (x + i + cellsWidth) % (cellsWidth);
                int adjustY = (y + j + cellsHeight) % (cellsHeight);
                count += cells[adjustX][adjustY];
            }
        }
        count -= cells[x][y];
        return count;
    }

    // TODO: survivalSet

    // TODO: survivalGet

    // TODO: birthSet

    // TODO: birthGet

    private int[][] clone2DArray(int[][] original) {
        int[][] copy = new int[original.length][original[0].length];
        for(int i = 0; i < original.length; i++) {
            copy[i] = original[i].clone();
        }
        return copy;
    }
}
