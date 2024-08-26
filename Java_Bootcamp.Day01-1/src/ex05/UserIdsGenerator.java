public class UserIdsGenerator {
    private static UserIdsGenerator instance;
    private Integer id = 0;

    public static UserIdsGenerator getInstance() {
        if (instance == null) {
            instance = new UserIdsGenerator();
        }
        return instance;
    }

    public int generateId() {
        return ++id;
    }
}