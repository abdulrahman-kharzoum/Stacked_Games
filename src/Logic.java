import Model.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Logic {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
//        UserLevel();
        List<Board> levels = new ArrayList<>();

ColoredSquare[] coloredSquares = {
                new ColoredSquare(new Position(0, 0), " ■ ", ConsoleColors.YELLOW),  // Green
                new ColoredSquare(new Position(2, 0), " ■ ", ConsoleColors.YELLOW),  // Green
                new ColoredSquare(new Position(0, 2), " ■ ", ConsoleColors.PURPLE),  // Yellow
                new ColoredSquare(new Position(2, 2), " ■ ", ConsoleColors.PURPLE),  // Yellow
//                new ColoredSquare(new Position(1, 3), " ■ ", ConsoleColors.BLUE),    // Blue
//                new ColoredSquare(new Position(2, 0), " ■ ", ConsoleColors.YELLOW),  // Yellow
//                new ColoredSquare(new Position(2, 1), " ■ ", ConsoleColors.BLUE),    // Blue
//                new ColoredSquare(new Position(2, 3), " ■ ", ConsoleColors.GREEN)    // Green
        };

        // Initialize walls with predefined positions
        Wall[] walls = {
//                new Wall(new Position(0, 0)),
//                new Wall(new Position(3, 3)),
//                new Wall(new Position(0, 3)),
//                new Wall(new Position(3, 0))
        };
//
        Board board = new Board(3, 3, coloredSquares, walls);
//        Board board = InitializeBoard();
        board.displayBoard();
        Structure.getAllPossibleMoves(board);
        Structure.applyMove(board,coloredSquares,Move.UP);


//
//        System.out.println( Structure.canMove(levels.getFirst(),Move.UP));
//        System.out.println( Structure.canMove(levels.getFirst(),Move.DOWN));
//        System.out.println( Structure.canMove(levels.getFirst(),Move.LEFT));
//        System.out.println( Structure.canMove(levels.getFirst(),Move.RIGHT));
//
//        Structure.applyMove(board,Move.LEFT);


    }
//    public static void UserLevel(){
//        System.out.print("Enter the number of levels: ");
//        int numberOfLevels = scanner.nextInt();
//
//        List<Board> levels = new ArrayList<>();
//
//        for (int i = 0; i < numberOfLevels; i++) {
//            System.out.println("\nInitializing Level " + (i + 1) + ":");
//            Board board = InitializeBoard();
//            levels.add(board);
//        }
//
//        for (int i = 0; i < levels.size(); i++) {
//            System.out.println("\nLevel " + (i + 1) + " Board:");
//            Structure.print(levels.get(i));
//        }
//    }
public static Board InitializeBoard() {
    System.out.print("Enter the size of the board X: ");
    int boardX = scanner.nextInt();
    System.out.print("Enter the size of the board Y: ");
    int boardY = scanner.nextInt();

    Set<String> occupiedPositions = new HashSet<>();

    // Colored Squares
    System.out.print("Enter the number of colored squares: ");
    int numberOfColoredSquares = scanner.nextInt();
    ColoredSquare[] coloredSquares = new ColoredSquare[numberOfColoredSquares];


    System.out.println("Choose a color for each colored square:");
    System.out.println("1. Red\n2. Green\n3. Yellow\n4. Blue\n5. Purple\n6. Cyan\n7. White");

    for (int i = 0; i < numberOfColoredSquares; i++) {
        while (true) {
            System.out.print("Enter the position of colored square " + (i + 1) + " (x y): ");
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            String pos = x + "," + y;

            if (isValidPosition(x, y, boardX, boardY) && !occupiedPositions.contains(pos)) {
                System.out.print("Enter your choice of color for square " + (i + 1) + ": ");
                int colorChoice = scanner.nextInt();
                String color = getColorFromChoice(colorChoice);

                coloredSquares[i] = new ColoredSquare(new Position(x, y), " ■ ", color);
                occupiedPositions.add(pos);
                break;
            } else {
                System.out.println("Invalid or occupied position. Please enter a unique position within the board boundaries.");
            }
        }
    }

    // Walls
    System.out.print("Enter the number of walls: ");
    int numberOfWalls = scanner.nextInt();
    Wall[] walls = new Wall[numberOfWalls];

    for (int i = 0; i < numberOfWalls; i++) {
        while (true) {
            System.out.print("Enter the position of wall " + (i + 1) + " (x y): ");
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            String pos = x + "," + y;

            if (isValidPosition(x, y, boardX, boardY) && !occupiedPositions.contains(pos)) {
                walls[i] = new Wall(new Position(x, y));
                occupiedPositions.add(pos);
                break;
            } else {
                System.out.println("Invalid or occupied position. Please enter a unique position within the board boundaries.");
            }
        }
    }


    return new Board(boardX, boardY, coloredSquares, walls);
}
    private static boolean isValidPosition(int x, int y, int boardX, int boardY) {
        return x >= 0 && x < boardX && y >= 0 && y < boardY;
    }

    private static String getColorFromChoice(int choice) {
        switch (choice) {
            case 1: return ConsoleColors.RED;
            case 2: return ConsoleColors.GREEN;
            case 3: return ConsoleColors.YELLOW;
            case 4: return ConsoleColors.BLUE;
            case 5: return ConsoleColors.PURPLE;
            case 6: return ConsoleColors.CYAN;
            case 7: return ConsoleColors.WHITE;
            default:
                System.out.println("Invalid choice. Defaulting to White.");
                return ConsoleColors.WHITE;
        }
    }
}
