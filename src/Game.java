import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

public class Game extends JPanel implements ActionListener, KeyListener {
    public static final int width = 1000;
    public static final int height = 600;

    public static final int cellSize = 25;
    public static final int playgroundWidth = width/cellSize;
    public static final int playgroundHeight = height/cellSize;

    private final Snake bobby;
    private final Food food;

    private final int fps = 13;
    private final Timer timer;

    private final StringBuilder keyboardBuffer;

    public Game() {
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.requestFocus();
        this.addKeyListener(this);

        bobby = new Snake(Color.GREEN);
        food = new Food(Color.RED);

        keyboardBuffer = new StringBuilder("R");

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
        g2.setColor(new Color(0x171717));
        g2.setStroke(new BasicStroke(1));

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
        if (keyboardBuffer.length() > 0) {
            bobby.setDirection(keyboardBuffer.charAt(0));
            keyboardBuffer.deleteCharAt(0);
        }

        bobby.advance();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> {
                if (bobby.getDirection() != 'L')
                    keyboardBuffer.append('R');
            }
            case KeyEvent.VK_LEFT, KeyEvent.VK_A -> {
                if (bobby.getDirection() != 'R')
                    keyboardBuffer.append('L');
            }
            case KeyEvent.VK_DOWN, KeyEvent.VK_S -> {
                if (bobby.getDirection() != 'U')
                    keyboardBuffer.append('D');
            }
            case KeyEvent.VK_UP, KeyEvent.VK_W -> {
                if (bobby.getDirection() != 'D')
                    keyboardBuffer.append('U');
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
