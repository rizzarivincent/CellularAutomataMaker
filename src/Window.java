import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Dimension;

public class Window extends Canvas {

    public Window(int width, int height, String title, Main main) {
        JFrame frame = new JFrame(title);
    }
}
