package Model;

import java.util.Objects;

public class Position {
    public int x;
    public int y;


    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }
    public Position clone() {
        return new Position(this.x, this.y);
    }


    @Override
    public String toString() {
        return "Model.Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }


    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

}
