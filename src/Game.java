import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Game extends JPanel implements ActionListener {
    public static final int width = 1000;
    public static final int height = 600;

    public static final int cellSize = 40;
    public static final int playgroundWidth = width/cellSize;
    public static final int playgroundHeight = height/cellSize;

    private final Snake bobby;
    private final Food food;

    private final int fps = 7;
    private final Timer timer;

    public Game() {
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.requestFocus();

        bobby = new Snake(Color.GREEN);
        food = new Food(Color.RED);

        timer = new Timer(1000/fps, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // enable antialiasing
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        drawPlayground(g2);
        drawFood(g2);
        drawSnake(g2);
    }

    private void drawPlayground(Graphics2D g2) {
        g2.setColor(new Color(0x2E2F34));

        for (int i = 0; i < height; i += cellSize) {
            g2.drawLine(0, i, width, i);
        }

        for (int i = 0; i < width; i += cellSize) {
            g2.drawLine(i, 0, i, height);
        }
    }

    private void drawFood(Graphics2D g2) {
        g2.setColor(food.getColor());
        g2.fillRect(food.getX()*cellSize, food.getY()*cellSize, cellSize, cellSize);
    }

    private void drawSnake(Graphics2D g2) {
        g2.setColor(bobby.getColor());

        List<Point> body = bobby.getBody();
        for (Point coord : body) {
            int x = coord.x;
            int y = coord.y;

            g2.fillRect(x*cellSize, y*cellSize, cellSize, cellSize);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        bobby.advance();
        repaint();
    }
}
