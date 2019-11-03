package pw.react.backend.reactbackend.Controllers;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        this("");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
