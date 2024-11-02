import Model.ColoredSquare;
import Model.EmptySquare;
import Model.Position;
import Model.Square;
import Model.Wall;

import java.util.List;

public class Board {
    public final int boardX;
    public final int boardY;
    public Square[][] squares;
    public int numOfColors;
    public List<ColoredSquare> coloredSquares;
    public Wall[] walls;

    // ANSI Reset
    public static final String RESET = "\033[0m";

    public Board(int x, int y, int numOfColors,List<ColoredSquare> coloredSquares, Wall[] walls) {
        this.boardX = x;
        this.boardY = y;
        this.coloredSquares = coloredSquares;
        this.walls = walls;
        this.squares = new Square[x][y];
        this.numOfColors = numOfColors;
         prepareBoard();
    }

    private void prepareBoard() {
        // Initialize  empty squares
        for (int i = 0; i < boardX; i++) {
            for (int j = 0; j < boardY; j++) {
                squares[i][j] = new EmptySquare(new Position(i, j));
            }
        }

        //  colored squares
        for (ColoredSquare coloredSquare : coloredSquares) {
            squares[coloredSquare.position.x][coloredSquare.position.y] = coloredSquare;
        }

        //  walls
        for (Wall wall : walls) {
            squares[wall.position.x][wall.position.y] = wall;
        }
    }

    public void displayBoard() {
        for (int i = 0; i < boardX; i++) {
            for (int j = 0; j < boardY; j++) {
                Square square = squares[i][j];
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
