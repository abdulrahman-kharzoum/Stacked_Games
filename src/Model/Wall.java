package Model;

public class Wall extends Square {

    public Wall(Position position) {
        this.position = position;
        this.type = ConsoleColors.PURPLE_BOLD+" # "+ConsoleColors.RESET;
    }
}
