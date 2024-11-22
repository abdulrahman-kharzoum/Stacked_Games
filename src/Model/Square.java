package Model;


import java.util.Objects;

public abstract class Square {
   public String type;
    public Position position;
   public abstract Square clone();

    public abstract SquareType getSquareType();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return Objects.equals(type, square.type) && Objects.equals(position, square.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, position);
    }
}
