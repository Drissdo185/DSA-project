import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PuzzleGame {
    private int steps = 0;
    private int seconds = 0;
    private JLabel stepsLabel;
    private JLabel timerLabel;
    private Timer timer;

    public PuzzleGame() {
        // Create a new JFrame
        JFrame frame = new JFrame("Puzzle Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(800, 800);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        stepsLabel = new JLabel("Steps: " + steps);
        stepsLabel.setBounds(10, 10, 100, 20);
        frame.add(stepsLabel);

        timerLabel = new JLabel("Time: " + seconds + "s");
        timerLabel.setBounds(10, 40, 100, 20);
        frame.add(timerLabel);

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seconds++;
                timerLabel.setText("Time: " + seconds + "s");
            }
        });
        timer.start();

        PuzzlePanel puzzlePanel = new PuzzlePanel(this);
        puzzlePanel.setBounds(100, 100, puzzlePanel.getPreferredSize().width, puzzlePanel.getPreferredSize().height);
        frame.add(puzzlePanel);

        frame.setVisible(true);
    }
    public void stopTimer() {
        timer.stop();
    }

    public void incrementSteps() {
        steps++;
        stepsLabel.setText("Steps: " + steps);
    }
}