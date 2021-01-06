import java.awt.*;

public class CellularAutomaton extends Canvas implements Runnable {

    private Thread thread;
    private boolean running = false;

    private int[][] cells;

    public CellularAutomaton(int width, int height, int scale) {
        cells = new int[width / scale][height / scale];
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

    }

    private void render() {

    }

    public void randomizeCells() {

    }
}
