import Model.ColoredSquare;
import Model.Position;
import Model.Wall;

public class Level {
    private Board board;

    public Level(int x, int y, ColoredSquare[] coloredSquares, Wall[] walls) {
        this.board = new Board(x, y, coloredSquares, walls);
    }

    public Board getBoard() {
        return board;
    }
}
