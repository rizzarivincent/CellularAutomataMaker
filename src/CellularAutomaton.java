import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class CellularAutomaton extends Canvas implements Runnable {

    private Thread thread;
    private boolean running = false;

    private int[][] cells;
    private final int cellsWidth;
    private final int cellsHeight;
    private final int scale;

    private boolean[] survival = new boolean[9];
    private boolean[] birth = new boolean[9];

    public CellularAutomaton(int width, int height, int scale) {
        cellsWidth = width / scale;
        cellsHeight = height / scale;
        this.scale = scale;
        cells = new int[cellsHeight][cellsWidth];
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
        for (int i = 0; i < cellsHeight; i++) {
            for (int j = 0; j < cellsWidth; j++) {
                neighbors = getNeighbors(i, j);
                if (oldCells[i][j] == 0 && birth[neighbors]) {
                    cells[i][j] = 1;
                }
                if (oldCells[i][j] == 1 && !survival[neighbors]) {
                    cells[i][j] = 0;
                }
            }
        }
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        //Creating a background
        g.setColor(Color.gray);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // Actually drawing the squares
        for(int i = 0; i < cellsHeight; i++) {
            for(int j = 0; j < cellsWidth; j++) {
                if (cells[i][j] == 1) {
                    g.setColor(Color.white);
                } else {
                    g.setColor(Color.black);
                }
                g.fillRect(j*scale, i*scale, scale - 1, scale - 1);
            }
        }

        g.dispose();
        bs.show();
    }

    public void randomizeCells() {
        Random random = new Random();

        for (int i = 0; i < cellsHeight; i++) {
            for (int j = 0; j < cellsWidth; j++) {
                cells[i][j] = random.nextInt(2);
            }
        }
    }

    // TODO: randomizeCell(double percentage) which allows for a percentage of alive cells to begin with

    private int getNeighbors(int x, int y) {
        int count = 0;
        for(int i = -1; i <= 1; i++) {
            for(int j = -1; j <= 1; j++) {
                int adjustX = (x + i + cellsHeight) % (cellsHeight);
                int adjustY = (y + j + cellsWidth) % (cellsWidth);
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
