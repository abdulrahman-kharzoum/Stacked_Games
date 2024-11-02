import Model.*;

import java.util.ArrayList;
import java.util.List;

public class Structure {
    public static void print(Board board) {
        board.displayBoard();
    }
    static boolean canMove(Board board, ColoredSquare coloredSquare, Move move) {
        boolean foundAnEmptySquare = false;
        Position nextPosition = getNextPosition(coloredSquare.position, move);

        while (!isOutOfBounds(nextPosition, board)) {
            Square targetSquare = board.squares[nextPosition.x][nextPosition.y];

            if (targetSquare.getSquareType() == SquareType.WALL) {
                return foundAnEmptySquare; // Stop if wall encountered
            } else if (targetSquare.getSquareType() == SquareType.EMPTY) {
                foundAnEmptySquare = true; // Mark that there’s an empty space in the path
                nextPosition = getNextPosition(nextPosition, move);
            } else if (targetSquare.getSquareType() == SquareType.COLORED) {
                ColoredSquare targetColoredSquare = (ColoredSquare) targetSquare;

                if (!targetColoredSquare.color.equals(coloredSquare.color)) {
                    // Check if this differently-colored square can also move in the same direction
                    if (!canMove(board, targetColoredSquare, move)) {
                        return false; // If the other colored square can’t move, return false
                    }
                }
                // Stop if it's a same-colored square (will only return true if found an empty space)
                return foundAnEmptySquare;
            }
        }

        return foundAnEmptySquare;
    }

    public static void applyMove(Board board, ColoredSquare[] coloredSquares, Move move) {
        boolean moved;

        do {
            moved = false;

            for (ColoredSquare coloredSquare : coloredSquares) {
                // Re-check if the square can move based on the updated board state
                if (canMove(board, coloredSquare, move)) {
                    applyMoveOneSquare(board, coloredSquare, move);
                    moved = true; // Set flag to true if any square moves
                }
            }

            // After each pass, update and print the possible moves for each square
            print(board);
            getAllPossibleMoves(board);

        } while (moved); // Continue until no more moves are possible
    }

    public static void applyMoveOneSquare(Board board, ColoredSquare coloredSquare, Move move) {
        Position currentPos = coloredSquare.position;
        Position nextPos = getNextPosition(currentPos, move);

        while (!isOutOfBounds(nextPos, board)) {
            Square targetSquare = board.squares[nextPos.x][nextPos.y];

            // Stop if we reach a wall or differently-colored square
            if (targetSquare.getSquareType() == SquareType.WALL ||
                    (targetSquare.getSquareType() == SquareType.COLORED &&
                            !((ColoredSquare) targetSquare).color.equals(coloredSquare.color))) {
                break;
            }

            // Update the board by moving the colored square to the next position
            board.squares[currentPos.x][currentPos.y] = new EmptySquare(currentPos); // Set old position to empty
            board.squares[nextPos.x][nextPos.y] = coloredSquare; // Move to new position
            coloredSquare.position = nextPos; // Update colored square's position

            // Advance to the next position in the direction of movement
            currentPos = nextPos;
            nextPos = getNextPosition(currentPos, move);
        }
    }

    public static void getAllPossibleMoves(Board board) {
        for (ColoredSquare coloredSquare : board.coloredSquares) {
            List<Move> possibleMoves = new ArrayList<>();


            for (Move move : Move.values()) { // enum values
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
