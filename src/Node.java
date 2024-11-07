public class Node {
    Node parent;
    Board board;
//Todo: Add and Store the path to the solution with action.

    public Node(Node parent, Board board) {
        this.parent = parent;
        this.board = board;
    }
    public void PrintNode(){
        System.out.println("Parent "+ parent);
        board.displayBoard();
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
