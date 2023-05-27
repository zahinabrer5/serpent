import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayAgainWindow extends JFrame implements ActionListener {
    private final JButton playAgainBtn;
    private final JButton exitBtn;
    private final Game game;

    public PlayAgainWindow(Game game, int score, int highScore) {
        this.setTitle("Play Again?");
        // force user to close window using exitBtn
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setResizable(false);

        this.game = game;
        String msg = "GAME OVER! Your score is "+score+(score == highScore ? " (NEW HIGH SCORE!!)" : "");
        JLabel msgLabel = new JLabel(msg);

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
        mainPanel.add(msgLabel, gbc);
        mainPanel.add(btnPanel, gbc);

        this.getRootPane().setDefaultButton(playAgainBtn);

        this.add(mainPanel);
        this.pack();

        this.setLocationRelativeTo(null);
//        this.setIconImage(new ImageIcon(getClass().getResource("logo.png")).getImage());
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
