import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

public class Snake {
    private final Color color;
    private final List<Point> body;

    private int x;
    private int y;

    private char direction;
    private int velocityX;
    private int velocityY;

    public Snake(Color color) {
        this.color = color;

        body = new ArrayList<>();
        body.add(new Point(2, 0));
        body.add(new Point(1, 0));
        body.add(new Point(0, 0));

        x = body.size()-1;
        y = 0;

        setDirection('R');
    }

    public Color getColor() {
        return color;
    }

    public List<Point> getBody() {
        return body;
    }

    public char getDirection() {
        return direction;
    }

    public void setDirection(char direction) {
        this.direction = direction;

        switch (direction) {
            case 'R' -> {
                velocityX = 1;
                velocityY = 0;
            }
            case 'L' -> {
                velocityX = -1;
                velocityY = 0;
            }
            case 'D' -> {
                velocityX = 0;
                velocityY = 1;
            }
            case 'U' -> {
                velocityX = 0;
                velocityY = -1;
            }
        }
    }

    public boolean isDead() {
        Set<Point> set = new HashSet<>();
        for (Point coord : body)
            set.add(coord);
        return set.size() != body.size();
    }

    public void grow() {
        body.add(body.get(0));
    }

    public void advance() {
        List<Point> oldBody = new ArrayList<>(body);

        // advance body
        for (int i = 1; i < body.size()-1; i++)
            body.set(i, oldBody.get(i-1));

        // advance tail
        if (body.size() > 1)
            body.set(body.size()-1, oldBody.get(body.size()-2));

        // advance head
        x = Math.floorMod(x+velocityX, Game.playgroundWidth);
        y = Math.floorMod(y+velocityY, Game.playgroundHeight);
        body.set(0, new Point(x, y));
    }
}
