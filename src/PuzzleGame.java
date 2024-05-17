import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

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

        PuzzlePanel puzzlePanel = new PuzzlePanel(this);
        puzzlePanel.setBounds(100, 100, puzzlePanel.getPreferredSize().width, puzzlePanel.getPreferredSize().height);
        frame.add(puzzlePanel);

        // Create a new JButton instance for the "Solver" button
        JButton solverButton = new JButton("Solver");
        solverButton.setBounds(650, 150, 100, 50); // Adjust the position and size as needed
        solverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create an instance of BFS
                BFS bfs = new BFS();

                // Get the initial state of the puzzle
                int[][] initialState = new int[3][3];
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        String buttonText = puzzlePanel.buttons[i][j].getText();
                        initialState[i][j] = buttonText.equals("") ? 0 : Integer.parseInt(buttonText);
                    }
                }

                // Solve the puzzle
                List<int[][]> solution = bfs.solve(initialState);

                if (solution == null || solution.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No solution found!");
                    return;
                }

                Timer solverTimer = new Timer(1000, null);
                solverTimer.addActionListener(new ActionListener() {
                    private int index = 0; // To keep track of the current step

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (index < solution.size()) {
                            int[][] state = solution.get(index);
                            for (int i = 0; i < 3; i++) {
                                for (int j = 0; j < 3; j++) {
                                    puzzlePanel.buttons[i][j].setText(state[i][j] == 0 ? "" : String.valueOf(state[i][j]));
                                }
                            }
                            index++;
                            incrementSteps(); // Increment steps after updating the state
                        } else {
                            // Stop the timer when all steps have been visualized
                            ((Timer) e.getSource()).stop();
                            PuzzleGame.this.timer.stop();
                            JOptionPane.showMessageDialog(frame, "The bot has finished playing. It took " + seconds + " seconds and " + steps + " steps.");
                        }
                    }
                });

                // Start the timer
                solverTimer.start();
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

    public void stopTimer() {
        timer.stop();
    }

    public void incrementSteps() {
        steps++;
        stepsLabel.setText("Steps: " + steps);
    }
}

