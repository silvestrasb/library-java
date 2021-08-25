package library.exception;

import lombok.Getter;

@Getter
public class EmailAlreadyExistsException extends RuntimeException {
    private final String email;

    public EmailAlreadyExistsException(String email) {
        this.email = email;
    }

}
