package Model;


public class EmptySquare extends Square {
    public EmptySquare(Position position) {
        this.position = position;
        this.type = " = ";
    }
}
