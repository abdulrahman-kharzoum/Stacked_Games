import Model.*;

import java.util.ArrayList;
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

    public  void applyMove(Board board, Move move) {
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

            // Check if the game is finished after each pass
            if (checkGameFinished(board)) {
                System.out.println("Game Finished");
                print(board);
                System.exit(0); // Exit
            }

        } while (anySquareMoved); // Continue until no squares move in a pass
        print(board);
        System.out.println();
        getAllPossibleMoves(board);

    }
    public  boolean checkGameFinished(Board board) {
        for (List<ColoredSquare> coloredSquareList : board.coloredSquaresByColor.values()) {
            if (coloredSquareList.size() > 1) {
                return false;
            }
        }
        return true; // Only one square per color
    }


    public  boolean isFinalState(Node node) {
        return checkGameFinished(node.board);
    }

    public void applyMoveOneSquare(Board board, ColoredSquare coloredSquare, Move move) {
        Position current = coloredSquare.position;
        Position next = getNextPosition(current, move);

        List<ColoredSquare> squaresToRemove = new ArrayList<>();

        while (!isOutOfBounds(next, board)) {
            Square targetSquare = board.squares[next.x][next.y];

            if (targetSquare.getSquareType() == SquareType.WALL) {
                break; // wall
            } else if (targetSquare.getSquareType() == SquareType.COLORED) {
                ColoredSquare targetColoredSquare = (ColoredSquare) targetSquare;

                // different color
                if (!targetColoredSquare.color.equals(coloredSquare.color)) {
                    break;
                }

                // same color
                List<ColoredSquare> sameColorSquares = board.coloredSquaresByColor.get(targetColoredSquare.colorCode);
                if (sameColorSquares.size() > 1) {
                    squaresToRemove.add(targetColoredSquare);
                }
            }

            // Move the square to the new position
            // leave empty space
            board.squares[current.x][current.y] = new EmptySquare(current);
            board.squares[next.x][next.y] = coloredSquare;
            coloredSquare.position = next;

            // next iteration
            current = next;
            next = getNextPosition(current, move);
        }

        // Remove  same-colored squares after movement
        board.coloredSquaresByColor.get(coloredSquare.colorCode).removeAll(squaresToRemove);

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
