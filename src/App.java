import javax.swing.*;
import java.awt.*;

public class App {

    private static final int WIDTH = 640;
    private static final int HEIGHT = WIDTH * 3 / 4;
    private static final int SCALE = 10;

    public static void main(String[] args) {
        // Creating the JFrame
        JFrame frame = new JFrame("Cellular Automata Engine");
        // Setting window size
        Dimension windowDimension = new Dimension(WIDTH, HEIGHT);
        frame.setPreferredSize(windowDimension);
        frame.setMinimumSize(windowDimension);
        frame.setMaximumSize(windowDimension);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Constructing and adding the cellular automaton Canvas & JPanel
        CellularAutomaton cellularAutomaton = new CellularAutomaton(WIDTH, HEIGHT, SCALE);
        ButtonPanel buttonPanel = new ButtonPanel(cellularAutomaton);
        frame.add(buttonPanel);
        frame.add(cellularAutomaton);

        frame.setVisible(true);
        cellularAutomaton.start();
    }
}
