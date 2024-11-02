package Model;


public class EmptySquare extends Square {
    public EmptySquare(Position position) {
        this.position = position;
        this.type = " = ";
    }

    @Override
    public SquareType getSquareType() {
        return SquareType.EMPTY;
    }
}
