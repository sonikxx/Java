public class UserNotFoundException extends RuntimeException { // non-checked exception
    public UserNotFoundException(String message) {
        super(message);
    }
}