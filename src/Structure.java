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
                return foundAnEmptySquare; // Stop if a wall is encountered
            } else if (targetSquare.getSquareType() == SquareType.EMPTY) {
                foundAnEmptySquare = true; // Mark that there's an empty space
                nextPosition = getNextPosition(nextPosition, move); // Continue checking further
            } else if (targetSquare.getSquareType() == SquareType.COLORED) {
                ColoredSquare targetColoredSquare = (ColoredSquare) targetSquare;

                // If it's a differently-colored square
                if (!targetColoredSquare.color.equals(coloredSquare.color)) {
                    // Check if this square can also move in the same direction
                    if (canMove(board, targetColoredSquare, move)) {
                        foundAnEmptySquare = true; // Allow move if the blocking square can move
                    } else {
                        return false; // If the other square can't move, stop here
                    }
                } else {
                    // If it's the same-colored square, treat it as part of the movable group
                    return true;
                }
                // If we reach here, return based on whether an empty space was found
                return foundAnEmptySquare;
            }
        }

        return foundAnEmptySquare; // Return true if an empty path was found in the while loop
    }

    public static void applyMove(Board board, ColoredSquare[] coloredSquares, Move move) {
        getAllPossibleMoves(board);

        boolean moved;

        do {
            moved = false;

            for (ColoredSquare coloredSquare : coloredSquares) {
                if (canMove(board, coloredSquare, move)) {
                    applyMoveOneSquare(board, coloredSquare, move);
                    moved = true;
                }
            }

        } while (moved);

        print(board);
        
    }

    public static void applyMoveOneSquare(Board board, ColoredSquare coloredSquare, Move move) {
        Position currentPos = coloredSquare.position;
        Position nextPos = getNextPosition(currentPos, move);

        while (!isOutOfBounds(nextPos, board)) {
            Square targetSquare = board.squares[nextPos.x][nextPos.y];

            if (targetSquare.getSquareType() == SquareType.WALL ||
                    targetSquare.getSquareType() == SquareType.COLORED) {
                ColoredSquare targetColoredSquare = (ColoredSquare) targetSquare;

                if (!targetColoredSquare.color.equals(coloredSquare.color)) {
                    // Check if the other square can move in the same direction
                    if (canMove(board, targetColoredSquare, move)) {
                        // Move the other square out of the way
                        applyMoveOneSquare(board, targetColoredSquare, move);
                    } else {
                        // If the other square can't move, stop the current square's movement
                        break;
                    }
                }
                else {
                    applyMoveOneSquare(board, targetColoredSquare, move);
                }
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
