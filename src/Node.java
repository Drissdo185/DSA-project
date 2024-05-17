public class Node {
    int[][] puzzle;
    Node parent;

    public Node(int[][] puzzle, Node parent) {
        this.puzzle = puzzle;
        this.parent = parent;
    }
}
