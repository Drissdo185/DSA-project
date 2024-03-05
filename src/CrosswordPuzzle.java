import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CrosswordPuzzle extends JFrame {
    private JFrame frame;
    private JPanel panel;
    private JTextField[][] grid;
    private List<String> words;

    public CrosswordPuzzle(int rows, int cols) {
        frame = new JFrame("Crossword Puzzle");
        panel = new JPanel(new GridLayout(rows, cols));
        grid = new JTextField[rows][cols];
        words = new ArrayList<>(); // Initialize the words list

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = new JTextField();
                panel.add(grid[i][j]);
            }
        }

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setSize(300, 300);
        frame.setVisible(true);
    }

    public void addWord(String word) {
        words.add(word);
    }

    public void generatePuzzle() {
        // Sort words by length in descending order
        Collections.sort(words, (w1, w2) -> Integer.compare(w2.length(), w1.length()));

        // Find the middle of the grid
        int middle = grid[0].length / 2;

        // Calculate the start column
        int startCol = middle - words.get(0).length() / 2;
        if (startCol < 0) startCol = 0;

        // Place the first word on the board
        placeWordOnBoard(words.get(0), 0, startCol, true);

        // Loop through the remaining words
        for (int i = 1; i < words.size(); i++) {
            String word = words.get(i);
            boolean placed = false;

            // Loop through all the words that are already on the board
            for (int row = 0; row < grid.length; row++) {
                for (int col = 0; col < grid[row].length; col++) {
                    // Check for horizontal and vertical placement
                    if (canPlaceWord(word, row, col, true) || canPlaceWord(word, row, col, false)) {
                        placeWordOnBoard(word, row, col, true);
                        placed = true;
                        break;
                    }
                }
                if (placed) break;
            }
        }
    }

    private boolean canPlaceWord(String word, int row, int col, boolean horizontal) {
        // Check if the word fits within the grid
        if (horizontal && col + word.length() > grid[0].length) return false;
        if (!horizontal && row + word.length() > grid.length) return false;

        // Check if the word intersects with any existing words
        for (int i = 0; i < word.length(); i++) {
            if (horizontal) {
                if (!grid[row][col + i].getText().isEmpty() && !grid[row][col + i].getText().equals(String.valueOf(word.charAt(i)))) {
                    return false;
                }
            } else {
                if (!grid[row + i][col].getText().isEmpty() && !grid[row + i][col].getText().equals(String.valueOf(word.charAt(i)))) {
                    return false;
                }
            }
        }

        return true;
    }

    public void placeWordOnBoard(String word, int row, int col, boolean isHorizontal) {
        if (isHorizontal) {
            for (int i = 0; i < word.length(); i++) {
                grid[row][col + i].setText(String.valueOf(word.charAt(i)));
            }
        } else {
            for (int i = 0; i < word.length(); i++) {
                grid[row + i][col].setText(String.valueOf(word.charAt(i)));
            }
        }
    }


}