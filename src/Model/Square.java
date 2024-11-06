package Model;


public abstract class Square {
   public String type;
    public Position position;

    public abstract Square clone();

    public abstract SquareType getSquareType();

}
