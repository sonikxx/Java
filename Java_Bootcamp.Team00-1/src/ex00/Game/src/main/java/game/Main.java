package game;

public class Main {
    public static void main(String[] args) {
        GameArgs jArgs = new GameArgs();
        jArgs.parsing(args);
        Settings settings = new Settings(jArgs.getProfile());
        settings.parse(jArgs);
        try {
            Game game = new Game(settings);
            game.play();
        } catch (IllegalParametersException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(-1);
        }
    }
}