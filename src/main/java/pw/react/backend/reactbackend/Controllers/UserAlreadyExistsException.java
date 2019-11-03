package pw.react.backend.reactbackend.Controllers;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        this("");
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}