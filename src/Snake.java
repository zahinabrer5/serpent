import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

public class Snake {
    private Color color;
    private List<List<Integer>> body;
    private char direction = 'R';

    public Snake(Color color) {
        this.color = color;
        body = new ArrayList<>();
        List<Integer> coord = new ArrayList<>();
        coord.add(0);
        coord.add(0);
        body.add(coord);
    }

    public Color getColor() {
        return color;
    }

    public List<List<Integer>> getBody() {
        return body;
    }

    public char getDirection() {
        return direction;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public boolean isDead() {
        Set<List<Integer>> set = new HashSet<>();
        for (List<Integer> coord : body)
            set.add(coord);
        return set.size() != body.size();
    }

    public void grow() {
        List<Integer> oldHead = body.get(0);
        advanceHead();
        body.add(1, oldHead);
    }

    public void advance() {
        List<List<Integer>> oldBody = new ArrayList<>(body);
        advanceHead();
        for (int i = 1; i < body.size(); i++)
            body.set(i, oldBody.get(i-1));
    }

    private void advanceHead() {
        switch (direction) {
            case 'R':
                body.get(0).set(0, (body.get(0).get(0)+1) % Game.playgroundWidth);
                break;
            case 'L':
                body.get(0).set(0, (body.get(0).get(0)-1) % Game.playgroundWidth);
                break;
            case 'D':
                body.get(0).set(1, (body.get(0).get(1)+1) % Game.playgroundHeight);
            case 'U':
                body.get(0).set(1, (body.get(0).get(1)-1) % Game.playgroundHeight);
                break;
            default:
                break;
        }
    }
}
