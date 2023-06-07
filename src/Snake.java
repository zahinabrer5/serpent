import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class Snake {
    private final Color color;
    // use a list of points instead of a list of lists
    // at first I used a list of lists, which produced some weird
    // errors in snake movement due to list references being copied
    // instead of their actual values being copied into new lists
    private final List<Point> body;

    private int x;
    private int y;

    private char direction;
    private int velocityX;
    private int velocityY;

    private int highScore = 0;

    // a keyboard buffer is used to ensure the snake doesn't show
    // weird behaviour when the user presses different keys in rapid
    // succession
    // this acts like a queue
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

    // basically sets the directions for the x and y components
    // velocity is a vector quantity, so it also needs a direction
    // (denoted by positive or negative here)
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

    // set the new high score, returns the old high score
    // to make it easier to check if the snake has beaten
    // its old high score
    public int setHighScore() {
        int oldHighScore = highScore;
        if (getScore() > highScore)
            highScore = getScore();
        return oldHighScore;
    }

    public void setName(String name) {
        this.name = name;
    }

    // the snake is dead if the head is found in the snake
    // outside the position the head should be in (the head
    // is in position 0 of the list representing the body)
    public boolean isDead() {
        for (int i = 1; i < body.size(); i++)
            if (body.get(i).equals(body.get(0)))
                return true;
        return false;
    }

    // add the head to the body again to grow the snake
    public void grow() {
        body.add(body.get(0));
        // print the body every growth for debugging
        System.out.println(name+" has grown: "+bodyFormattedString());
    }

    // return a nice formatted string for body
    public String bodyFormattedString() {
        StringBuilder sb = new StringBuilder("[");
        for (Point coord : body) {
            sb.append("(").append(coord.x).append(", ").append(coord.y).append("), ");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.deleteCharAt(sb.length()-1);
        sb.append("]");
        return sb.toString();
    }

    public void advance() {
        List<Point> oldBody = new ArrayList<>(body);

        // advance head
        // use floor mod to allow snake to pass through playground borders
        // floor mod is different from % because it takes into account of
        // negative values
        x = Math.floorMod(x+velocityX, Game.playgroundWidth);
        y = Math.floorMod(y+velocityY, Game.playgroundHeight);
        body.set(0, new Point(x, y));

        // advance body by shifting body array
        for (int i = 1; i < body.size(); i++)
            body.set(i, oldBody.get(i-1));
    }

    // use the next keyboard input and pop it off the buffer
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

    // grow the snake if the food has the same coordinates as
    // the snake's head, return the boolean representing whether
    // the snake grew or not
    public boolean eaten(Food food) {
        if (food.equals(body.get(0))) {
            grow();
            return true;
        }
        return false;
    }
}
