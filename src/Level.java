import Model.ColoredSquare;
import Model.Position;
import Model.Wall;

import java.util.List;

public class Level {
    private Board board;

    public Level(int x, int y, int  numOfColors, List<ColoredSquare> coloredSquares, Wall[] walls) {
        this.board = new Board(x, y,numOfColors, coloredSquares, walls);
    }

    public Board getBoard() {
        return board;
    }
}
