import java.awt.*;
import java.util.List;
import java.util.Random;

public class Food extends Point {
    private final Color color;
    private final List<Point> snakeBody;
    private final Random rand;

    public Food(Color color, Snake snake) {
        this.color = color;
        this.snakeBody = snake.getBody();
        rand = new Random();

        respawn();
    }

    public Color getColor() {
        return color;
    }

    public void respawn() {
        do {
            x = rand.nextInt(Game.playgroundWidth);
            y = rand.nextInt(Game.playgroundHeight);
        } while (snakeBodyContains());
    }

    private boolean snakeBodyContains() {
        for (Point coord : snakeBody)
            if (coord.x == x && coord.y == y)
                return true;
        return false;
    }

    public boolean eaten() {
        return this.equals(snakeBody.get(0));
    }
}
