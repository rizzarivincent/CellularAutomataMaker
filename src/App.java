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

        // Constructing the cellular automaton
    CellularAutomaton cellularAutomaton = new CellularAutomaton(WIDTH, HEIGHT, SCALE);
        // Making the two JPanels
        JPanel cellPanel = new JPanel();
        ButtonPanel buttonPanel = new ButtonPanel(cellularAutomaton);

        frame.setVisible(true);
    }
}
