import javax.swing.*;
import java.awt.*;
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

        Font labelFont = new Font("Arial", Font.BOLD, 20);
        stepsLabel = new JLabel("Steps: " + steps);
        stepsLabel.setFont(labelFont);
        stepsLabel.setBounds(650, 50, 150, 50); // Adjusted the width to fit the text
        frame.add(stepsLabel);

        timerLabel = new JLabel("Time: " + seconds + "s");
        timerLabel.setFont(labelFont);
        timerLabel.setBounds(650, 100, 150, 50); // Adjusted the width to fit the text
        frame.add(timerLabel);

        // reset button
        JButton resetButton = new JButton("Reset");
        resetButton.setBounds(650, 200, 100, 50); // Adjust the position and size as needed
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PuzzleGame.this.resetGame();
            }
        });
        frame.add(resetButton);

        PuzzlePanel puzzlePanel = new PuzzlePanel(this);
        puzzlePanel.setBounds(100, 100, puzzlePanel.getPreferredSize().width, puzzlePanel.getPreferredSize().height);
        frame.add(puzzlePanel);

        // Create an instance of the Bot using BFS class
        BotBFS botBFS = new BotBFS(puzzlePanel, this, frame);
        BotDFS botDFS = new BotDFS(puzzlePanel, this, frame);

        // Create a new JButton instance for the "Solver" button
        JButton solverButton = new JButton("Solver");
        solverButton.setBounds(650, 150, 100, 50); // Adjust the position and size as needed
        solverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Stop the timer and reset seconds to 0
                PuzzleGame.this.stopTimer();
                PuzzleGame.this.setSeconds(0);
                timerLabel.setText("Time: " + PuzzleGame.this.getSeconds() + "s");

                // Show a dialog with two options
                Object[] options = {"BFS", "DFS"};
                int n = JOptionPane.showOptionDialog(frame,
                        "Which algorithm do you want to use to solve the puzzle?",
                        "Choose an algorithm",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]);

                // Start solving the puzzle
                if (n == JOptionPane.YES_OPTION) {
                    botBFS.autoSolve();
                }else{
                    botDFS.autoSolve();
                }

                // Restart the timer
                PuzzleGame.this.startTimer();

            }
        });

        // Add the "Solver" button to the PuzzleGame frame
        frame.add(solverButton);

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seconds++;
                timerLabel.setText("Time: " + seconds + "s");
            }
        });
        timer.start();

        frame.setVisible(true);
    }
    
    public void resetGame(){
        this.steps = 0;
        this.seconds = 0;

        stepsLabel.setText("Steps: " + steps);
        timerLabel.setText("Time: " + seconds + "s");

        // Dung thoi gian
        this.startTimer();

        // reset xog thi bat dau thoi gian lai
        this.startTimer();
    }

    public void stopTimer() {
        timer.stop();
    }

    public void incrementSteps() {
        steps++;
        stepsLabel.setText("Steps: " + steps);
    }

    public int getSteps() {
        return steps;
    }
    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public void startTimer() {
        timer.start();
    }
}

