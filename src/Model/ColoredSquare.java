package Model;

import java.awt.*;
import java.util.Objects;

public class ColoredSquare extends Square{
    public String color;
    public int colorCode;
    public ColoredSquare(Position position, String type, String color) {
        this.position = position;
        this.type = type;
        this.color = color;
        this.colorCode = getColorCode();
    }

    @Override
    public SquareType getSquareType() {
        return SquareType.COLORED;
    }

    public Color getColor() {
        return convertToColor(color);
    }
    public int getColorCode() {
        return switch (color) {
            case ConsoleColors.RED -> 1;
            case ConsoleColors.GREEN -> 2;
            case ConsoleColors.YELLOW -> 3;
            case ConsoleColors.BLUE -> 4;
            case ConsoleColors.PURPLE -> 5;
            case ConsoleColors.CYAN -> 6;
            case ConsoleColors.WHITE -> 7;
            default -> 8;
        };

    }

    private Color convertToColor(String ansiColor) {
        return switch (ansiColor) {
            case ConsoleColors.RED -> Color.RED;
            case ConsoleColors.GREEN -> Color.GREEN;
            case ConsoleColors.YELLOW -> Color.YELLOW;
            case ConsoleColors.BLUE -> Color.BLUE;
            case ConsoleColors.PURPLE -> new Color(128, 0, 128);
            case ConsoleColors.CYAN -> Color.CYAN;
            case ConsoleColors.BLACK -> Color.BLACK;
            case ConsoleColors.WHITE -> Color.WHITE;
            default -> Color.GRAY;
        };
    }
    @Override
    public ColoredSquare clone() {
        return new ColoredSquare(this.position.clone(), this.type, this.color);
    }
//
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColoredSquare that = (ColoredSquare) o;
        return  colorCode == that.colorCode &&
                Objects.equals(color, that.color) &&
                Objects.equals(position, that.position);
    }

    @Override
    public int hashCode() {
//        System.out.println("colored square");
//        System.out.println("type "+type+"position "+position);
//        System.out.println("color "+color+"colorCode "+colorCode);
//        System.out.println("hash: "+Objects.hash(type, position));
        return Objects.hash(super.hashCode(), color, colorCode);
    }



}
