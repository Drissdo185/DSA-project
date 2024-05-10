import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class PuzzlePanel extends JPanel {
    private JButton[][] buttons;
    private Point emptySpace;
    private Timer timer;
    private int elapsedTime;
    private int steps;
    private PuzzleGame puzzleGame;

    public PuzzlePanel(PuzzleGame puzzleGame) {
        this.puzzleGame = puzzleGame;
        setLayout(new GridLayout(2, 2));
        buttons = new JButton[2][2];
        initializeButtons();
        shuffleButtons();

        setPreferredSize(new Dimension(500, 500));

        // Initialize timer
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                elapsedTime++;
                // Update UI with new elapsed time
                // For example, you can update a JLabel with the elapsed time
            }
        });
        timer.start();
    }

    private void initializeButtons() {
        ActionListener buttonClickListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                swapWithEmptySpace(button);
                steps++;
                puzzleGame.incrementSteps();
                if (isSolved()) {
                    timer.stop();
                    puzzleGame.stopTimer();
                    JOptionPane.showMessageDialog(null, "You solved the puzzle in " + elapsedTime + " seconds with " + steps + " steps!");

                }
            }
        };

        int counter = 1;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if (counter == 4) {
                    buttons[i][j] = new JButton("");
                    emptySpace = new Point(i, j);
                } else {
                    buttons[i][j] = new JButton(String.valueOf(counter));
                }
                buttons[i][j].setBackground(Color.GRAY);
                buttons[i][j].setForeground(Color.WHITE);
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 50));
                buttons[i][j].addActionListener(buttonClickListener);
                add(buttons[i][j]);
                counter++;
            }
        }
    }

    private void shuffleButtons() {
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            int row = random.nextInt(2);
            int col = random.nextInt(2);
            swapWithEmptySpace(buttons[row][col]);
        }
    }

    private void swapWithEmptySpace(JButton button) {
        int buttonRow = -1;
        int buttonCol = -1;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if (buttons[i][j] == button) {
                    buttonRow = i;
                    buttonCol = j;
                }
            }
        }

        if (buttonRow == -1 || buttonCol == -1) {
            return;
        }

        if (Math.abs(emptySpace.x - buttonRow) + Math.abs(emptySpace.y - buttonCol) > 1) {
            return;
        }

        buttons[emptySpace.x][emptySpace.y].setText(button.getText());
        button.setText("");
        emptySpace = new Point(buttonRow, buttonCol);
    }

    private boolean isSolved() {
        int counter = 1;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if (counter == 4) {
                    if (!buttons[i][j].getText().equals("")) {
                        return false;
                    }
                } else {
                    if (!buttons[i][j].getText().equals(String.valueOf(counter))) {
                        return false;
                    }
                }
                counter++;
            }
        }
        return true;
    }
}
