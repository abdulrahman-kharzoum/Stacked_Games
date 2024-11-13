import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Node {
    Node parent;
    Board board;
    Move action;
//Todo: Add and Store the path to the solution with action.

    public Node(Node parent, Board board) {
        this.parent = parent;
        this.board = board;
    }
    public void PrintNode(){
        System.out.println("move "+ action);
        board.displayBoard();
    }

    // Method to retrieve the path from the root to this node
    public List<Move> getPath() {
        List<Move> path = new ArrayList<>();
        Node current = this;

        while (current.parent != null) {
            path.add(current.action);  // Add the action that led to this node
            current = current.parent;
        }

        Collections.reverse(path);  // Reverse to get the path from root to solution
        return path;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Node other = (Node) obj;
        return board.equals(other.board);
    }

    @Override
    public int hashCode() {
        return board.hashCode();
    }

}
