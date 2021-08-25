package library.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {

    private final String username;

    public UserNotFoundException(String username) {
        this.username = username;
    }
}
