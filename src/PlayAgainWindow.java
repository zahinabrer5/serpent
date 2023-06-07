import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class PlayAgainWindow extends JFrame implements ActionListener {
    private final JButton playAgainBtn;
    private final JButton exitBtn;
    private final Game game;

    public PlayAgainWindow(Game game, Snake... snakes) {
        this.setTitle("Play Again?");
        // force user to close window using exitBtn
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setResizable(false);

        this.game = game;

        // use list because there could be multiple snakes
        // after "GAME OVER!!" there will be a message for each snake
        List<StringBuilder> msgs = new ArrayList<>();
        msgs.add(new StringBuilder("GAME OVER!!"));
        for (Snake snake : snakes) {
            String name = snake.getName();
            int score = snake.getScore();

            // build the messages that shows up on the play again window
            StringBuilder msg = new StringBuilder();
            if (snake.isDead())
                msg.append(name).append("'s dead! ");
            msg.append(name).append("'s score is ").append(score);
            if (score > snake.setHighScore())
                msg.append(" (NEW HIGH SCORE!!)");
            msgs.add(msg);
        }

        this.playAgainBtn = new JButton("Play Again!");
        playAgainBtn.addActionListener(this);
        this.exitBtn = new JButton("Exit");
        exitBtn.addActionListener(this);

        // wrapper panel containing the buttons
        JPanel btnPanel = new JPanel();
        btnPanel.add(playAgainBtn);
        btnPanel.add(exitBtn);

        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(400, 150));
        mainPanel.setLayout(new GridBagLayout());

        // use GridBagConstraints to centre components in middle of window
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        for (StringBuilder msg : msgs) {
            // create different JLabels for each message
            JLabel label = new JLabel(msg.toString());
            mainPanel.add(label, gbc);
        }
        mainPanel.add(btnPanel, gbc);

        this.getRootPane().setDefaultButton(playAgainBtn);

        this.add(mainPanel);
        this.pack();

        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon(getClass().getResource("logo.png")).getImage());
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(playAgainBtn)) {
            game.reset();
            this.setVisible(false);
            this.dispose();
        }
        else if (e.getSource().equals(exitBtn)) {
            System.exit(0); // (successfully) exit
        }
    }
}
