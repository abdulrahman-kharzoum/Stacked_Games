public enum Move {
    UP(-1,0),
    DOWN(1,0),
    LEFT(0,-1),
    RIGHT(0,1);
    private int moveX;
    private int moveY;
Move(int moveX, int moveY){
    this.moveX = moveX;
    this.moveY = moveY;
    }

    public int getMoveX() {
        return moveX;
    }

    public int getMoveY() {
        return moveY;
    }
}
