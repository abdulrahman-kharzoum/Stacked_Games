import Model.ColoredSquare;
import Model.EmptySquare;
import Model.Position;
import Model.Square;
import Model.Wall;

import java.util.List;

import java.util.Map;

public class Board {
    public final int boardX;
    public final int boardY;
    public Square[][] squares;
    public int numOfColors;
    public Map<Integer, List<ColoredSquare>> coloredSquaresByColor;
    public Wall[] walls;

    private static final String RESET = "\033[0m";

    public Board(int x, int y, int numOfColors, Map<Integer, List<ColoredSquare>> coloredSquaresByColor, Wall[] walls) {
        this.boardX = x;
        this.boardY = y;
        this.numOfColors = numOfColors;
        this.coloredSquaresByColor = coloredSquaresByColor;
        this.walls = walls;
        this.squares = new Square[x][y];
        prepareBoard();
    }
    //Empty Spaces
    private void prepareBoard() {
        for (int i = 0; i < boardX; i++) {
            for (int j = 0; j < boardY; j++) {
                squares[i][j] = new EmptySquare(new Position(i, j));
            }
        }

        for (List<ColoredSquare> coloredSquareList : coloredSquaresByColor.values()) {
            for (ColoredSquare coloredSquare : coloredSquareList) {
                squares[coloredSquare.position.x][coloredSquare.position.y] = coloredSquare;
            }
        }

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
