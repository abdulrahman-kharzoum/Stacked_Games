import Model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Structure {

    public static void print(Board board) {
        board.displayBoard();
    }

     boolean canMove(Board board, ColoredSquare coloredSquare, Move move) {
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
                    return canMove(board, targetColoredSquare, move);
                } else {
                    return true;
                }
            }

            nextPosition = getNextPosition(nextPosition, move); // Continue checking further
        }

        return false;
    }

    public void applyMove(Board board, Move move) {
        boolean anySquareMoved;

        do {
            anySquareMoved = false;

            for (int i = 0; i < board.boardX; i++) {
                for (int j = 0; j < board.boardY; j++) {
                    if (board.squares[i][j] instanceof ColoredSquare square) {
                        if (canMove(board, square, move)) {
                            applyMoveOneSquare(board, square, move);
                            anySquareMoved = true;
                        }
                    }
                }
            }


            if (checkGameFinished(board)) {
                System.out.println("Game Finished");
                print(board);
                break;
            }

        } while (anySquareMoved);
    }



    public boolean checkGameFinished(Board board) {
        // Check if each color only has one square remaining
        for (Map.Entry<Integer, List<ColoredSquare>> entry : board.coloredSquaresByColor.entrySet()) {
            List<ColoredSquare> coloredSquareList = entry.getValue();

            // If no squares or more than one square of a color, the game is not finished
            if (coloredSquareList == null || coloredSquareList.size() != 1) {
                return false;
            }
        }

        // Ensure that all expected colors are present
        if (board.coloredSquaresByColor.size() != board.numOfColors) {
            return false; // Missing colors
        }

        return true; // Game finished if only one square per color and all colors are present
    }

    public boolean isFinalState(Node node) {
        Map<Integer, List<ColoredSquare>> map = node.board.coloredSquaresByColor;

        // Check if exactly one square exists for each color
        for (Map.Entry<Integer, List<ColoredSquare>> entry : map.entrySet()) {
            if (entry.getValue().size() != 1) {
                return false; // More than one or none of this color
            }
        }
        return true;
    }


    public void applyMoveOneSquare(Board board, ColoredSquare coloredSquare, Move move) {
        Position current = coloredSquare.position;
        Position next = getNextPosition(current, move);

        List<ColoredSquare> squaresToRemove = new ArrayList<>(); // This will track squares that need to be removed

        while (!isOutOfBounds(next, board)) {
            Square targetSquare = board.squares[next.x][next.y];

            if (targetSquare.getSquareType() == SquareType.WALL) {
                break; // Wall hit, stop moving
            } else if (targetSquare.getSquareType() == SquareType.COLORED) {
                ColoredSquare targetColoredSquare = (ColoredSquare) targetSquare;

                // Different color: stop moving
                if (!targetColoredSquare.color.equals(coloredSquare.color)) {
                    break;
                }

                // Same color: check if there's a conflict
                List<ColoredSquare> sameColorSquares = board.coloredSquaresByColor.get(targetColoredSquare.colorCode);

                // Ensure not removing the last square of a color
                if (sameColorSquares.size() > 1) {
                    squaresToRemove.add(targetColoredSquare); // Only remove if there's more than one square of that color
                }
            }

            // Move the square to the new position
            board.squares[current.x][current.y] = new EmptySquare(current); // Clear the old position
            board.squares[next.x][next.y] = coloredSquare; // Place the colored square in the new position
            coloredSquare.position = next; // Update the colored square's position

            // Move to the next position
            current = next;
            next = getNextPosition(current, move);
        }

        // Now remove the squares that should actually be removed from the map (only if not the last square)
        if (!squaresToRemove.isEmpty()) {
            // Check if it's not the last square of the color before removing
            for (ColoredSquare squareToRemove : squaresToRemove) {
                List<ColoredSquare> sameColorSquares = board.coloredSquaresByColor.get(squareToRemove.colorCode);
                // Only remove the square if there are more than 1 square of that color
                if (sameColorSquares.size() > 1) {
                    sameColorSquares.remove(squareToRemove); // Remove the square
                }
            }
        }
//
//        System.out.println("After move:");
//        board.displayBoard();
//        System.out.println(board.coloredSquaresByColor);
    }

    public  void getAllPossibleMoves(Board board) {
        for (Map.Entry<Integer, List<ColoredSquare>> entry : board.coloredSquaresByColor.entrySet()) {
            for (ColoredSquare coloredSquare : entry.getValue()) {
                List<Move> possibleMoves = new ArrayList<>();
                for (Move move : Move.values()) {
                    if (canMove(board, coloredSquare, move)) {
                        possibleMoves.add(move);
                    }
                }
                System.out.println("Possible moves for ColoredSquare with code " + coloredSquare.colorCode + " at position " + coloredSquare.position + ": " + possibleMoves);
            }
        }
    }

    private  Position getNextPosition(Position current, Move move) {
        return new Position(current.x + move.getMoveX(), current.y + move.getMoveY());
    }

    private  boolean isOutOfBounds(Position pos, Board board) {
        return pos.x < 0 || pos.x >= board.boardX || pos.y < 0 || pos.y >= board.boardY;
    }
}
