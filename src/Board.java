import Model.ColoredSquare;
import Model.EmptySquare;
import Model.Position;
import Model.Square;
import Model.Wall;

import java.util.*;

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

    public Board cloneBoard() {
        // Clone the coloredSquaresByColor map
        Map<Integer, List<ColoredSquare>> clonedColoredSquaresByColor = new HashMap<>();
        for (Map.Entry<Integer, List<ColoredSquare>> entry : this.coloredSquaresByColor.entrySet()) {
            List<ColoredSquare> clonedList = new ArrayList<>();
            for (ColoredSquare square : entry.getValue()) {
                clonedList.add(square.clone()); // Assuming ColoredSquare has a clone method
            }
            clonedColoredSquaresByColor.put(entry.getKey(), clonedList);
        }

        // Clone squares array
        Square[][] clonedSquares = new Square[this.boardX][this.boardY];
        for (int i = 0; i < this.boardX; i++) {
            for (int j = 0; j < this.boardY; j++) {
                clonedSquares[i][j] = this.squares[i][j].clone(); // Assuming Square and its subclasses have clone methods
            }
        }

        // Create a new Board
        return new Board(this.boardX, this.boardY, this.numOfColors, clonedColoredSquaresByColor, this.walls.clone());
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;

        // Compare each square on the board
        for (int i = 0; i < boardX; i++) {
            for (int j = 0; j < boardY; j++) {
                if (!this.squares[i][j].equals(board.squares[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(boardX, boardY, numOfColors);

        // Combine the hash of each square in the grid
        for (int i = 0; i < boardX; i++) {
            for (int j = 0; j < boardY; j++) {
                result = 31 * result + squares[i][j].hashCode();
            }
        }
        return result;
    }


}
