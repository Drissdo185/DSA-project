import java.util.*;

public class BFS {
    private Queue<Node> queue;
    private Set<String> visited;
    private int[][] goal;

    public BFS() {
        queue = new LinkedList<>();
        visited = new HashSet<>();
        goal = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
    }

    public List<int[][]> solve(int[][] initial) {
        Node startNode = new Node(initial, null);
        queue.add(startNode);

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();

            if (isGoal(currentNode)) {
                return getSolution(currentNode);
            }

            for (Node neighbor : getNeighbors(currentNode)) {
                String neighborString = Arrays.deepToString(neighbor.puzzle);
                if (!visited.contains(neighborString)) {
                    visited.add(neighborString);
                    queue.add(neighbor);
                }
            }
        }

        return null;  // Return null if no solution is found
    }

    private boolean isGoal(Node node) {
        return Arrays.deepEquals(node.puzzle, goal);
    }

    private List<Node> getNeighbors(Node node) {
    List<Node> neighbors = new ArrayList<>();
    int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};  // Up, down, left, right

    // Find the empty space
    int emptyX = -1, emptyY = -1;
    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            if (node.puzzle[i][j] == 0) {
                emptyX = i;
                emptyY = j;
                break;
            }
        }
        if (emptyX != -1) {
            break;
        }
    }

    // Generate neighbors by swapping the empty space with a neighboring tile
    for (int[] direction : directions) {
        int newX = emptyX + direction[0];
        int newY = emptyY + direction[1];

        if (newX >= 0 && newX < 3 && newY >= 0 && newY < 3) {
            int[][] puzzleCopy = new int[3][3];
            for (int i = 0; i < 3; i++) {
                puzzleCopy[i] = node.puzzle[i].clone();
            }

            // Swap the empty space with the neighboring tile
            puzzleCopy[emptyX][emptyY] = puzzleCopy[newX][newY];
            puzzleCopy[newX][newY] = 0;

            neighbors.add(new Node(puzzleCopy, node));
        }
    }

    return neighbors;
}

    private List<int[][]> getSolution(Node goalNode) {
        List<int[][]> solution = new ArrayList<>();
        Node currentNode = goalNode;
        while (currentNode != null) {
            solution.add(0, currentNode.puzzle);  // Add the state to the front of the solution
            currentNode = currentNode.parent;
        }
        return solution;
    }
}
