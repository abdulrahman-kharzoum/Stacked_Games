package Model;


public class EmptySquare extends Square {
    public EmptySquare(Position position) {
        this.position = position;
        this.type = " = ";
    }

    @Override
    public Square clone() {
        return new EmptySquare(this.position.clone());
    }

    @Override
    public SquareType getSquareType() {
        return SquareType.EMPTY;
    }
}
