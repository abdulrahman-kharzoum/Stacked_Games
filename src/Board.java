import Model.ColoredSquare;
import Model.EmptySquare;
import Model.Position;
import Model.Square;
import Model.Wall;

public class Board {
    private final int boardX;
    private final int boardY;
    public Square[][] board;
    public ColoredSquare[] coloredSquares;
    public Wall[] walls;

    // ANSI Reset
    public static final String RESET = "\033[0m";

    public Board(int x, int y, ColoredSquare[] coloredSquares, Wall[] walls) {
        this.boardX = x;
        this.boardY = y;
        this.coloredSquares = coloredSquares;
        this.walls = walls;
        this.board = new Square[x][y];
        prepareBoard();
    }

    private void prepareBoard() {
        // Initialize board with empty squares
        for (int i = 0; i < boardX; i++) {
            for (int j = 0; j < boardY; j++) {
                board[i][j] = new EmptySquare(new Position(i, j));  // Initialize each square as empty
            }
        }

        // Place colored squares
        for (ColoredSquare coloredSquare : coloredSquares) {
            board[coloredSquare.position.x][coloredSquare.position.y] = coloredSquare;
        }

        // Place walls
        for (Wall wall : walls) {
            board[wall.position.x][wall.position.y] = wall;
        }
    }

    public void displayBoard() {
        for (int i = 0; i < boardX; i++) {
            for (int j = 0; j < boardY; j++) {
                Square square = board[i][j];
                if (square instanceof ColoredSquare) {
                    System.out.print(((ColoredSquare) square).color + square.type + RESET + " ");
                } else {
                    System.out.print(square.type + " ");
                }
            }
            System.out.println();
        }
    }
}
