package game;

public class IllegalParametersException extends RuntimeException { // non-checked exception
    public IllegalParametersException(String message) {
        super(message);
    }
}