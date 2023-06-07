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
    // width and height of the playing window in terms of cellSize
    // useful for looping through the playground like in playgroundFilled()
    public static final int playgroundWidth = width/cellSize;
    public static final int playgroundHeight = height/cellSize;

    private Snake bobby;
    private Snake dobby;
    private Food apple;

    private final int fps = 13;
    private final Timer timer;

    private final boolean multiplayer;

    public Game(Object[] data) {
        // unpack data array into variables
        boolean multiplayer = (boolean)data[0];
        String snake1Name = (String)data[1];
        String snake2Name = (String)data[2];

        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // used for reduced lag on animation
        this.setFocusable(true);
        this.requestFocus();
        this.addKeyListener(this);

        bobby = new Snake(Color.GREEN, snake1Name);
        apple = new Food(Color.RED, bobby);

        this.multiplayer = multiplayer;
        if (multiplayer) {
            dobby = new Snake(Color.BLUE, snake2Name);
            apple.setSnakes(bobby, dobby);
        }

        // make a timer that calls actionPerfomed every fps times in a second
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
        drawAllSnakes(g2);
    }

    // draw grid lines for playground
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
        g2.setColor(apple.getColor());
        g2.fillRect(apple.x*cellSize, apple.y*cellSize, cellSize, cellSize);
    }

    private void drawAllSnakes(Graphics2D g2) {
        drawSnake(g2, bobby);

        if (multiplayer)
            drawSnake(g2, dobby);
    }

    private void drawSnake(Graphics2D g2, Snake snake) {
        g2.setColor(snake.getColor());

        List<Point> body = snake.getBody();
        for (Point coord : body) {
            int x = coord.x;
            int y = coord.y;

            g2.fillRect(x*cellSize, y*cellSize, cellSize, cellSize);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // add this check because during testing I noticed the game
        // just hangs when the playground gets completely filled by a snake
        // I couldn't find a simple way to stop the game, so I added an instant
        // kill method
        if (playgroundFilled()) {
            System.out.println("Playground got filled somehow!!!");
            System.exit(0);
        }

        eatingActions();

        gameActions(bobby);

        if (multiplayer)
            gameActions(dobby);

        repaint();
    }

    // handle actions related to when a snake eats the food or not
    // the food should respawn when either snake eats it
    private void eatingActions() {
        boolean bobbyEaten = bobby.eaten(apple);
        boolean dobbyEaten = multiplayer && dobby.eaten(apple);
        if (bobbyEaten || dobbyEaten)
            apple.respawn();
    }

    private boolean playgroundFilled() {
        for (int i = 0; i < playgroundHeight; i++) {
            for (int j = 0; j < playgroundWidth; j++) {
                Point coord = new Point(j, i);
                if (!bobby.getBody().contains(coord))
                    return false;
                if (multiplayer && !dobby.getBody().contains(coord))
                    return false;
            }
        }
        return true;
    }

    private void gameActions(Snake snake) {
        snake.updateKeyboardBuffer();
        snake.advance();
        if (snake.isDead()) {
            timer.stop();
            new PlayAgainWindow(this, this.bobby, this.dobby);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_RIGHT -> {
                if (bobby.getDirection() != 'L')
                    bobby.appendToKeyboardBuffer('R');
            }
            case KeyEvent.VK_LEFT -> {
                if (bobby.getDirection() != 'R')
                    bobby.appendToKeyboardBuffer('L');
            }
            case KeyEvent.VK_DOWN -> {
                if (bobby.getDirection() != 'U')
                    bobby.appendToKeyboardBuffer('D');
            }
            case KeyEvent.VK_UP -> {
                if (bobby.getDirection() != 'D')
                    bobby.appendToKeyboardBuffer('U');
            }
        }
        // use WASD keys for snake 2
        if (multiplayer) {
            switch (key) {
                case KeyEvent.VK_D -> {
                    if (dobby.getDirection() != 'L')
                        dobby.appendToKeyboardBuffer('R');
                }
                case KeyEvent.VK_A -> {
                    if (dobby.getDirection() != 'R')
                        dobby.appendToKeyboardBuffer('L');
                }
                case KeyEvent.VK_S -> {
                    if (dobby.getDirection() != 'U')
                        dobby.appendToKeyboardBuffer('D');
                }
                case KeyEvent.VK_W -> {
                    if (dobby.getDirection() != 'D')
                        dobby.appendToKeyboardBuffer('U');
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void reset() {
        bobby = new Snake(Color.GREEN, "Bobby");
        apple = new Food(Color.RED, bobby);

        if (multiplayer) {
            dobby = new Snake(Color.BLUE, "Dobby");
            apple.setSnakes(bobby, dobby);
        }

        timer.start();
    }
}
