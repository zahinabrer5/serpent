import java.awt.*;
import java.util.List;
import java.util.Random;

public class Food {
    private Color color;
    private int x;
    private int y;
    private static Random rand;

    public Food(Color color) {
        this.color = color;
        rand = new Random();

        x = 1;
        y = 0;

        spawn();
    }

    public Color getColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private void spawn() {
        x = rand.nextInt(Game.playgroundWidth);
        y = rand.nextInt(Game.playgroundHeight);
    }

    public void respawn(List<List<Integer>> body) {
        do {
            spawn();
        } while (snakeBodyContains(body));
    }

    private boolean snakeBodyContains(List<List<Integer>> body) {
        for (List<Integer> coord : body)
            if (coord.get(0) == x && coord.get(1) == y)
                return true;
        return false;
    }
}
