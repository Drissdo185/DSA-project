import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BotDFS {
    private DFS dfs;
    private PuzzlePanel puzzlePanel;
    private PuzzleGame puzzleGame;
    private JFrame frame;

    public BotDFS(PuzzlePanel puzzlePanel, PuzzleGame puzzleGame, JFrame frame) {
        this.dfs = new DFS();
        this.puzzlePanel = puzzlePanel;
        this.puzzleGame = puzzleGame;
        this.frame = frame;
    }

    public void autoSolve() {
        // Get the initial state of the puzzle
        int[][] initialState = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonText = puzzlePanel.buttons[i][j].getText();
                initialState[i][j] = buttonText.equals("") ? 0 : Integer.parseInt(buttonText);
            }
        }

        // Solve the puzzle
        List<int[][]> solution = dfs.solve(initialState);

        if (solution == null || solution.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No solution found!");
            return;
        }

        Timer solverTimer = new Timer(10, null);
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
                    puzzleGame.incrementSteps(); // Increment steps after updating the state
                } else {
                    // Stop the timer when all steps have been visualized
                    ((Timer) e.getSource()).stop();
                    puzzleGame.stopTimer();
                    JOptionPane.showMessageDialog(frame, "The bot has finished playing. It took " + puzzleGame.getSeconds() + " seconds and " + puzzleGame.getSteps() + " steps.");
                }
            }
        });

        // Start the timer
        solverTimer.start();
    }
}