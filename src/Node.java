public class Node {
    Node parent;
    Board board;

    public Node(Node parent, Board board) {
        this.parent = parent;
        this.board = board;
    }
}
