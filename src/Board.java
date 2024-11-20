import Model.ColoredSquare;
import Model.EmptySquare;
import Model.Position;
import Model.Square;
import Model.Wall;

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
                clonedList.add(square.clone()); // Ensure a deep clone of each square
            }
            clonedMap.put(entry.getKey(), clonedList);
        }

        Wall[] clonedWalls = Arrays.copyOf(this.walls, this.walls.length);
        return new Board(this.boardX, this.boardY, this.numOfColors, clonedMap, clonedWalls);
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
    public int getHeuristic() {
        int remainingSquares = this.getNumOfSquaresLeft();
        int manhattanDistance = calculateManhattanDistance(this);
        int clusterPenalty = calculateMaxDistanceBetweenSameColorSquares(this);

        int heuristic = remainingSquares * 10 + manhattanDistance * 2  ;


        return heuristic;
    }


    private Position findNearestTarget(Board board, ColoredSquare square) {
        Position nearest = null;
        int minDistance = Integer.MAX_VALUE;

        for (int i = 0; i < board.boardX; i++) {
            for (int j = 0; j < board.boardY; j++) {
                Square targetSquare = board.squares[i][j];
                if (targetSquare instanceof ColoredSquare target && target.colorCode == square.colorCode) {
                    int distance = Math.abs(square.position.x - i) + Math.abs(square.position.y - j);
                    if (distance < minDistance) {
                        minDistance = distance;
                        nearest = new Position(i, j);
                    }
                }
            }
        }


        return nearest != null ? nearest : square.position;
    }

    private int calculateManhattanDistance(Board board) {
        int totalDistance = 0;

        for (int i = 0; i < board.boardX; i++) {
            for (int j = 0; j < board.boardY; j++) {
                if (board.squares[i][j] instanceof ColoredSquare square) {
                    Position goalPosition = findNearestTarget(board, square);
                    if (goalPosition != null) {
                        int distance = Math.abs(square.position.x - goalPosition.x) +
                                Math.abs(square.position.y - goalPosition.y);
                        totalDistance += distance;

                    }
                }
            }
        }


        return totalDistance;
    }

    private int calculateMaxDistanceBetweenSameColorSquares(Board board) {
        int penalty = 0;

        for (List<ColoredSquare> coloredSquares : board.coloredSquaresByColor.values()) {
            int maxDistance = calculateFarthestDistance(coloredSquares);

            penalty += maxDistance;
        }


        return penalty;
    }

    private int calculateFarthestDistance(List<ColoredSquare> coloredSquares) {
        int maxDistance = 0;

        for (ColoredSquare p1 : coloredSquares) {
            for (ColoredSquare p2 : coloredSquares) {
                int distance = Math.abs(p1.position.x - p2.position.x) + Math.abs(p1.position.y - p2.position.y);
                maxDistance = Math.max(maxDistance, distance);
            }
        }
        return maxDistance;
    }


}
