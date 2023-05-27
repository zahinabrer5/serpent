import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class Snake {
    private final Color color;
    private final List<Point> body;

    private int x;
    private int y;

    private char direction;
    private int velocityX;
    private int velocityY;

    private int highScore = 0;

    private StringBuilder keyboardBuffer;

    private String name;

    public Snake(Color color, String name) {
        this.color = color;
        this.name = name;

        body = new ArrayList<>();
        body.add(new Point(0, 0));

        x = 0;
        y = 0;

        setDirection('R');

        resetKeyboardBuffer();
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

    public int getScore() {
        return body.size()-1;
    }

    public int getHighScore() {
        return highScore;
    }

    public String getName() {
        return name;
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

    public int setHighScore() {
        int oldHighScore = highScore;
        if (getScore() > highScore)
            highScore = getScore();
        return oldHighScore;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDead() {
        for (int i = 1; i < body.size(); i++)
            if (body.get(i).equals(body.get(0)))
                return true;
        return false;
    }

    public void grow() {
        body.add(body.get(0));
    }

    public void advance() {
        List<Point> oldBody = new ArrayList<>(body);

        // advance head
        x = Math.floorMod(x+velocityX, Game.playgroundWidth);
        y = Math.floorMod(y+velocityY, Game.playgroundHeight);
        body.set(0, new Point(x, y));

        // advance body
        for (int i = 1; i < body.size(); i++)
            body.set(i, oldBody.get(i-1));
    }

    public void updateKeyboardBuffer() {
        if (this.keyboardBuffer.length() > 0) {
            this.setDirection(this.keyboardBuffer.charAt(0));
            this.keyboardBuffer.deleteCharAt(0);
        }
    }

    public void resetKeyboardBuffer() {
        if (keyboardBuffer == null)
            keyboardBuffer = new StringBuilder();
        else
            this.keyboardBuffer.setLength(0);
        this.keyboardBuffer.append('R');
    }

    public void appendToKeyboardBuffer(char c) {
        this.keyboardBuffer.append(c);
    }

    public boolean eaten(Food food) {
        if (food.equals(body.get(0))) {
            grow();
            return true;
        }
        return false;
    }
}
