import Model.ColoredSquare;
import Model.EmptySquare;
import Model.Position;
import Model.Square;
import Model.Wall;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Board {
    public final int boardX;
    public final int boardY;
    public Square[][] squares;
    public int numOfColors;
    public Map<Integer, List<ColoredSquare>> coloredSquaresByColor;

    public List<ColoredSquare> coloredSquares;
    public Wall[] walls;

    private static final String RESET = "\033[0m";

    public Board(int x, int y, int numOfColors, Map<Integer, List<ColoredSquare>> coloredSquaresByColor, Wall[] walls) {
        this.boardX = x;
        this.boardY = y;
        this.numOfColors = numOfColors;
        this.coloredSquaresByColor = coloredSquaresByColor;
        this.walls = walls;
        this.squares = new Square[x][y];

        this.coloredSquares = new ArrayList<>();

        for (List<ColoredSquare> coloredSquareList : coloredSquaresByColor.values()) {
            this.coloredSquares.addAll(coloredSquareList);
        }
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
        Map<Integer, List<ColoredSquare>> clonedMap = new HashMap<>();
        for (Map.Entry<Integer, List<ColoredSquare>> entry : this.coloredSquaresByColor.entrySet()) {
            List<ColoredSquare> clonedList = new ArrayList<>();
            for (ColoredSquare square : entry.getValue()) {
                clonedList.add(square.clone());
            }
            clonedMap.put(entry.getKey(), clonedList);
        }

        return new Board(this.boardX, this.boardY, this.numOfColors, clonedMap, this.walls.clone());
    }

    public int getNumOfSquaresLeft(){
        int count = 0;
        for (int i = 0; i < boardX; i++) {
            for (Square square : squares[i]) {
                if (square instanceof ColoredSquare) {
                    count++;
                }
            }

        }
        return count;
    }
    public int getNumOfSquaresLeftWithSameColor(int colorCode){
        int count = 0;
        for (int i = 0; i < boardX; i++) {
            for (Square square : squares[i]) {
                if (square instanceof ColoredSquare) {
                    if (((ColoredSquare) square).colorCode == colorCode) {
                        count++;
                    }
                }
            }

        }
        return count;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;

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

        for (int i = 0; i < boardX; i++) {
            for (int j = 0; j < boardY; j++) {
                result = 31 * result + squares[i][j].hashCode();
            }
        }
        return result;

//        List<Object> objectList = new ArrayList<>();
//        for (int i = 0; i < boardY; i++) {
//            for (Square s : squares[i]){
//                objectList.add(s);
//            }
//
//        }
//        return Objects.hash(objectList);
    }



}
