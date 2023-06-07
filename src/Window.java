import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    public Window(Object[] data) {
        this.setTitle("Serpent");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        Game game = new Game(data);

        this.add(game);
        this.pack();

        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon(getClass().getResource("logo.png")).getImage());
        this.setVisible(true);
    }
}
