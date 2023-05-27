import java.awt.*;
import java.util.Random;

public class Food extends Point {
    private final Color color;
    private Snake[] snakes;
    private final Random rand;

    public Food(Color color, Snake... snakes) {
        this.color = color;
        this.snakes = snakes;
        rand = new Random();

        respawn();
    }

    public Color getColor() {
        return color;
    }

    public void setSnakes(Snake... snakes) {
        this.snakes = snakes;
    }

    public void respawn() {
        do {
            x = rand.nextInt(Game.playgroundWidth);
            y = rand.nextInt(Game.playgroundHeight);
        } while (snakeBodyContains());
    }

    private boolean snakeBodyContains() {
        for (Snake snake : snakes)
            for (Point coord : snake.getBody())
                if (coord.x == x && coord.y == y)
                    return true;
        return false;
    }
}
