import Model.ColoredSquare;
import Model.ConsoleColors;
import Model.Position;
import Model.Wall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Level {
    private Board board;

    // Constructor
    public Level(int x, int y, int numOfColors, Map<Integer, List<ColoredSquare>> coloredSquares, Wall[] walls) {
        this.board = new Board(x, y, numOfColors, coloredSquares, walls);
    }

    public Board getBoard() {
        return board;
    }


    public static Level createLevel1() {
        List<ColoredSquare> initialColoredSquares = new ArrayList<>();
        initialColoredSquares.add(new ColoredSquare(new Position(0, 0), " ■ ", ConsoleColors.BLUE));
        initialColoredSquares.add(new ColoredSquare(new Position(0, 1), " ■ ", ConsoleColors.BLUE));
        initialColoredSquares.add(new ColoredSquare(new Position(0, 2), " ■ ", ConsoleColors.BLUE));
        initialColoredSquares.add(new ColoredSquare(new Position(0, 3), " ■ ", ConsoleColors.BLUE));
        initialColoredSquares.add(new ColoredSquare(new Position(0, 4), " ■ ", ConsoleColors.BLUE));
        initialColoredSquares.add(new ColoredSquare(new Position(0, 5), " ■ ", ConsoleColors.BLUE));
        initialColoredSquares.add(new ColoredSquare(new Position(1, 5), " ■ ", ConsoleColors.BLUE));
        initialColoredSquares.add(new ColoredSquare(new Position(2, 5), " ■ ", ConsoleColors.BLUE));
        initialColoredSquares.add(new ColoredSquare(new Position(3, 5), " ■ ", ConsoleColors.BLUE));
        initialColoredSquares.add(new ColoredSquare(new Position(4, 5), " ■ ", ConsoleColors.BLUE));
        initialColoredSquares.add(new ColoredSquare(new Position(5, 5), " ■ ", ConsoleColors.BLUE));
        initialColoredSquares.add(new ColoredSquare(new Position(1, 0), " ■ ", ConsoleColors.PURPLE));
        initialColoredSquares.add(new ColoredSquare(new Position(2, 0), " ■ ", ConsoleColors.PURPLE));
        initialColoredSquares.add(new ColoredSquare(new Position(3, 0), " ■ ", ConsoleColors.PURPLE));
        initialColoredSquares.add(new ColoredSquare(new Position(3, 1), " ■ ", ConsoleColors.PURPLE));
        initialColoredSquares.add(new ColoredSquare(new Position(3, 2), " ■ ", ConsoleColors.PURPLE));
        initialColoredSquares.add(new ColoredSquare(new Position(4, 0), " ■ ", ConsoleColors.PURPLE));
        initialColoredSquares.add(new ColoredSquare(new Position(4, 1), " ■ ", ConsoleColors.PURPLE));
        initialColoredSquares.add(new ColoredSquare(new Position(4, 2), " ■ ", ConsoleColors.PURPLE));
        initialColoredSquares.add(new ColoredSquare(new Position(5, 0), " ■ ", ConsoleColors.PURPLE));
        initialColoredSquares.add(new ColoredSquare(new Position(5, 1), " ■ ", ConsoleColors.PURPLE));
        initialColoredSquares.add(new ColoredSquare(new Position(5, 2), " ■ ", ConsoleColors.PURPLE));
        initialColoredSquares.add(new ColoredSquare(new Position(5, 3), " ■ ", ConsoleColors.PURPLE));
        initialColoredSquares.add(new ColoredSquare(new Position(5, 4), " ■ ", ConsoleColors.PURPLE));
        initialColoredSquares.add(new ColoredSquare(new Position(1, 3), " ■ ", ConsoleColors.PURPLE));
        initialColoredSquares.add(new ColoredSquare(new Position(1, 4), " ■ ", ConsoleColors.PURPLE));
        initialColoredSquares.add(new ColoredSquare(new Position(2, 3), " ■ ", ConsoleColors.PURPLE));
        initialColoredSquares.add(new ColoredSquare(new Position(2, 4), " ■ ", ConsoleColors.PURPLE));

        Map<Integer, List<ColoredSquare>> coloredSquaresByColor = new HashMap<>();
        for (ColoredSquare square : initialColoredSquares) {
            coloredSquaresByColor
                    .computeIfAbsent(square.colorCode, k -> new ArrayList<>())
                    .add(square);
        }

        Wall[] walls = new Wall[]{
                new Wall(new Position(1, 1)),
                new Wall(new Position(1, 2)),
                new Wall(new Position(2, 1)),
                new Wall(new Position(2, 2)),
                new Wall(new Position(3, 3)),
                new Wall(new Position(3, 4)),
                new Wall(new Position(4, 3)),
                new Wall(new Position(4, 4))
        };

        return new Level(6, 6, 2, coloredSquaresByColor, walls);
    }
}
