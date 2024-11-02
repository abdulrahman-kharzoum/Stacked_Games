package Model;

public class ColoredSquare extends Square{
    public String color;

    public ColoredSquare(Position position, String type, String color) {
        this.position = position;
        this.type = type;
        this.color = color;
    }
}
