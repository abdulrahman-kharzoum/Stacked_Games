package Model;

public class Wall extends Square {

    public Wall(Position position) {
        this.position = position;
        this.type = ConsoleColors.BLACK+" # "+ConsoleColors.RESET;
    }

    @Override
    public Square clone() {
        return new Wall(this.position.clone());
    }


    @Override
    public SquareType getSquareType() {
        return SquareType.WALL;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
