public enum Move {
    UP(-1, 0, 1),
    DOWN(1, 0, 2),
    LEFT(0, -1, 5),
    RIGHT(0, 1, 6);
    private int moveX;
    private int moveY;
    private int cost;
    public static int movesCount = 0;
    Move(int moveX, int moveY, int cost) {
        this.moveX = moveX;
        this.moveY = moveY;
        this.cost = cost;
    }


    public int getCost() {
        return cost;
    }

    public int getMoveX() {
        return moveX;
    }

    public int getMoveY() {
        return moveY;
    }
}
