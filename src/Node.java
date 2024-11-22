import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Node {
    Node parent;
    Board board;
    Move action;
    int cost;
    int heuristic;

    public Node(Node parent, Board board) {
        this.parent = parent;
        this.board = board;
    }
    public Node(Node parent, Board board,int cost, Move action) {
        this.parent = parent;
        this.board = board;
        this.cost= cost;
        this.action = action;
    }
    public Node(Node parent, Board board,Move action,int cost, int heuristic) {
        this.parent = parent;
        this.board = board;
        this.action = action;
        this.cost= cost;
        this.heuristic= heuristic;
    }
    public void PrintNode(){
        System.out.println("ACTION : "+ action);
        System.out.println("Cost : "+ cost);
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

    public int getCost() {
        return cost;
    }
    public int getAstarCost() {
        return cost + heuristic;
    }
    public int getHeuristic() {
        return heuristic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return cost == node.cost && heuristic == node.heuristic && Objects.equals(parent, node.parent) && Objects.equals(board, node.board) && action == node.action;
    }

    @Override
    public int hashCode() {
        return Objects.hash(parent, board, action, cost, heuristic);
    }
}
