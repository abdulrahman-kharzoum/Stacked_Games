import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Node {
    Node parent;
    Board board;
    Move action;

    public Node(Node parent, Board board) {
        this.parent = parent;
        this.board = board;
    }
    public void PrintNode(){
        System.out.println("ACTION : "+ action);
        board.displayBoard();
    }


    public List<Move> getPath() {
        List<Move> path = new ArrayList<>();
        Node current = this;

        while (current.parent != null) {
            path.add(current.action);
            current = current.parent;
        }

        Collections.reverse(path);
        return path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(parent, node.parent) && Objects.equals(board, node.board) && action == node.action;
    }

    @Override
    public int hashCode() {
        return Objects.hash(parent, board, action);
    }
}
