package Model;

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
}
