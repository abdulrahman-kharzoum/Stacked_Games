import Model.*;

import java.util.ArrayList;
import java.util.List;

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
                }
            }

            nextPosition = getNextPosition(nextPosition, move); // Continue checking further
        }

        return false;
    }

    public static void applyMove(Board board, ColoredSquare[] coloredSquares, Move move) {
        boolean moved;

        do {
            moved = false;
            for (ColoredSquare coloredSquare : coloredSquares) {
                if (canMove(board, coloredSquare, move)) {
                    applyMoveOneSquare(board, coloredSquare, move);
                    moved = true;
                }
            }
            print(board);
        } while (moved);
    }

    public static void applyMoveOneSquare(Board board, ColoredSquare coloredSquare, Move move) {
        Position current = coloredSquare.position;
        Position next = getNextPosition(current, move);

        while (!isOutOfBounds(next, board)) {
            Square targetSquare = board.squares[next.x][next.y];

            if (targetSquare.getSquareType() == SquareType.WALL) {
                break; // Stop if wall is reached
            } else if (targetSquare.getSquareType() == SquareType.COLORED) {
                ColoredSquare targetColoredSquare = (ColoredSquare) targetSquare;

                if (!targetColoredSquare.color.equals(coloredSquare.color) && !canMove(board, targetColoredSquare, move)) {
                    break; // Blocked by different color square that can't move
                }
            }

            // Update board to reflect new position
            board.squares[current.x][current.y] = new EmptySquare(current);
            board.squares[next.x][next.y] = coloredSquare;
            coloredSquare.position = next;

            // Advance to the next position
            current = next;
            next = getNextPosition(current, move);
        }
    }

    public static void getAllPossibleMoves(Board board) {
        for (ColoredSquare coloredSquare : board.coloredSquares) {
            List<Move> possibleMoves = new ArrayList<>();
            for (Move move : Move.values()) {
                if (canMove(board, coloredSquare, move)) {
                    possibleMoves.add(move);
                }
            }
            System.out.println("Possible moves for ColoredSquare at position " + coloredSquare.position + ": " + possibleMoves);
        }
    }

    private static Position getNextPosition(Position current, Move move) {
        return new Position(current.x + move.getMoveX(), current.y + move.getMoveY());
    }

    private static boolean isOutOfBounds(Position pos, Board board) {
        return pos.x < 0 || pos.x >= board.boardX || pos.y < 0 || pos.y >= board.boardY;
    }
}
