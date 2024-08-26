package game;

import com.diogonunes.jcolor.Attribute;

import java.io.FileReader;
import java.util.Properties;

public class Settings {
    String profile;
    int size;
    int wallsCount;
    int enemiesCount;
    Attribute emptyColor;
    String path;
    Attribute goalColor;
    Attribute enemyColor;
    Attribute playerColor;
    Attribute wallColor;
    Attribute textColor = Attribute.BLACK_TEXT();
    char enemyChar;
    char playerChar;
    char wallChar;
    char goalChar;
    char emptyChar;

    public Settings(String profile) {
        this.profile = profile;
        if (profile.equals("dev"))
            path = "Game/src/main/resources/application-dev.properties";
        else if (profile.equals("production"))
            path = "Game/src/main/resources/application-production.properties";
        else
            throw new IllegalParametersException("Profile not found");
    }

    public Settings parse(GameArgs jArgs) {
        this.size = jArgs.getSize();
        this.wallsCount = jArgs.getWallsCount();
        this.enemiesCount = jArgs.getEnemiesCount();
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(path));
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(-1);
        }
        this.emptyColor = getAttribute(properties.getProperty("empty.color"));
        this.goalColor = getAttribute(properties.getProperty("goal.color"));
        this.enemyColor = getAttribute(properties.getProperty("enemy.color"));
        this.playerColor = getAttribute(properties.getProperty("player.color"));
        this.wallColor = getAttribute(properties.getProperty("wall.color"));
        this.enemyChar = properties.getProperty("enemy.char").charAt(0);
        this.playerChar = properties.getProperty("player.char").charAt(0);
        this.wallChar = properties.getProperty("wall.char").charAt(0);
        this.goalChar = properties.getProperty("goal.char").charAt(0);
        this.emptyChar = properties.getProperty("empty.char").charAt(0);
        return this;
    }

    private Attribute getAttribute(String color) {
        switch (color) {
            case "BLACK":
                return Attribute.BLACK_BACK();
            case "RED":
                return Attribute.RED_BACK();
            case "GREEN":
                return Attribute.GREEN_BACK();
            case "YELLOW":
                return Attribute.YELLOW_BACK();
            case "BLUE":
                return Attribute.BLUE_BACK();
            case "MAGENTA":
                return Attribute.MAGENTA_BACK();
            case "CYAN":
                return Attribute.CYAN_BACK();
            case "WHITE":
                return Attribute.WHITE_BACK();
            default:
                return Attribute.BLACK_BACK();
        }
    }

}
