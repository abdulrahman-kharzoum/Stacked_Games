package Model;

import java.awt.*;

public class ColoredSquare extends Square{
    public String color;

    public ColoredSquare(Position position, String type, String color) {
        this.position = position;
        this.type = type;
        this.color = color;
    }

    @Override
    public SquareType getSquareType() {
        return SquareType.COLORED;
    }

    public Color getColor() {
        return convertToColor(color);
    }

    private Color convertToColor(String ansiColor) {
        switch (ansiColor) {
            case ConsoleColors.RED:
                return Color.RED;
            case ConsoleColors.GREEN:
                return Color.GREEN;
            case ConsoleColors.YELLOW:
                return Color.YELLOW;
            case ConsoleColors.BLUE:
                return Color.BLUE;
            case ConsoleColors.PURPLE:
                return new Color(128, 0, 128); // Custom color for PURPLE
            case ConsoleColors.CYAN:
                return Color.CYAN;
            case ConsoleColors.BLACK:
                return Color.BLACK;
            case ConsoleColors.WHITE:
                return Color.WHITE;
            default:
                return Color.GRAY; // Fallback color
        }
    }
}
