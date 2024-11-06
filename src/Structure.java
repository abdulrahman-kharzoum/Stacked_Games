import Model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Structure {

    public static void print(Board board) {
        board.displayBoard();
    }

    static boolean canMove(Board board, ColoredSquare coloredSquare, Move move) {
        Position nextPosition = getNextPosition(coloredSquare.position, move);

        while (!isOutOfBounds(nextPosition, board)) {
            Square targetSquare = board.squares[nextPosition.x][nextPosition.y];

            if (targetSquare.getSquareType() == SquareType.WALL) {
                return false;
            } else if (targetSquare.getSquareType() == SquareType.EMPTY) {
                return true;
            } else if (targetSquare.getSquareType() == SquareType.COLORED) {
                ColoredSquare targetColoredSquare = (ColoredSquare) targetSquare;

                if (!targetColoredSquare.color.equals(coloredSquare.color)) {
                    // different -color =>  can't move
                    return false;
                }else {
                    return true;
                }
            }

            nextPosition = getNextPosition(nextPosition, move); // Continue checking further
        }

        return false;
    }
    public static void applyMove(Board board, Move move) {
        for (int i = 0; i < board.boardX; i++) {
            for (int j = 0; j < board.boardY; j++) {
                if (board.squares[i][j] instanceof ColoredSquare){
                    ColoredSquare square = (ColoredSquare) board.squares[i][j];
                    if (canMove(board,square,move)){
                        applyMoveOneSquare(board,square,move);
                    }
                }
                if (checkGameFinished(board)) {
                    System.out.println("Game Finished");
                    print(board);
                    System.exit(0); // Exit
                }

            }

        }
        print(board);
        getAllPossibleMoves(board);
    }

    public static void applyMove1(Board board, Move move) {
        boolean moved;

        do {
            moved = false;

            // Iterate through each color's list of squares
            for (List<ColoredSquare> coloredSquareList : board.coloredSquaresByColor.values()) {
                for (ColoredSquare coloredSquare : new ArrayList<>(coloredSquareList)) {
                    if (canMove(board, coloredSquare, move)) {
                        applyMoveOneSquare(board, coloredSquare, move);
                        moved = true;
                    }
                }
            }

            // Check if the game is finished
            if (checkGameFinished(board)) {
                System.out.println("Game Finished");
                print(board);
                System.exit(0); // Exit
            }

            print(board);
            getAllPossibleMoves(board);
        } while (moved);
    }


    public static boolean checkGameFinished(Board board) {
        for (List<ColoredSquare> coloredSquareList : board.coloredSquaresByColor.values()) {
            if (coloredSquareList.size() > 1) {
                return false;
            }
        }
        return true; // Only one square per color
    }


    public static void applyMoveOneSquare(Board board, ColoredSquare coloredSquare, Move move) {
        Position current = coloredSquare.position;
        Position next = getNextPosition(current, move);

        List<ColoredSquare> squaresToRemove = new ArrayList<>();

        while (!isOutOfBounds(next, board)) {
            Square targetSquare = board.squares[next.x][next.y];

            if (targetSquare.getSquareType() == SquareType.WALL) {
                break;
            } else if (targetSquare.getSquareType() == SquareType.COLORED) {
                ColoredSquare targetColoredSquare = (ColoredSquare) targetSquare;

                if (!targetColoredSquare.color.equals(coloredSquare.color) && !canMove(board, targetColoredSquare, move)) {
                    break;
                }

                if (targetColoredSquare.color.equals(coloredSquare.color)) {
                    List<ColoredSquare> sameColorSquares = board.coloredSquaresByColor.get(targetColoredSquare.colorCode);
                    if (sameColorSquares.size() > 1) {
                        squaresToRemove.add(targetColoredSquare);
                    }
                }
            }

            board.squares[current.x][current.y] = new EmptySquare(current);
            board.squares[next.x][next.y] = coloredSquare;
            coloredSquare.position = next;

            current = next;
            next = getNextPosition(current, move);
        }

        board.coloredSquaresByColor.get(coloredSquare.colorCode).removeAll(squaresToRemove);
    }


    public static void getAllPossibleMoves(Board board) {
        for (Map.Entry<Integer, List<ColoredSquare>> entry : board.coloredSquaresByColor.entrySet()) {
            for (ColoredSquare coloredSquare : entry.getValue()) {
                List<Move> possibleMoves = new ArrayList<>();
                for (Move move : Move.values()) {
                    if (canMove(board, coloredSquare, move)) {
                        possibleMoves.add(move);
                    }
                }
                System.out.println("Possible moves for ColoredSquare with code "+coloredSquare.colorCode+" at position " + coloredSquare.position + ": " + possibleMoves);
            }
        }
    }

    private static Position getNextPosition(Position current, Move move) {
        return new Position(current.x + move.getMoveX(), current.y + move.getMoveY());
    }

    private static boolean isOutOfBounds(Position pos, Board board) {
        return pos.x < 0 || pos.x >= board.boardX || pos.y < 0 || pos.y >= board.boardY;
    }
}
