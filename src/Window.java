import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    public Window() {
        this.setTitle("Serpent");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        JPanel panels = new JPanel();
        panels.setLayout(new BorderLayout());

        Game game = new Game(true);
        panels.add(game, BorderLayout.PAGE_END);

        this.add(panels);
        this.pack();

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
