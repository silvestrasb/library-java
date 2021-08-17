package library.exception;

import lombok.Getter;

@Getter
public class BookNotFoundException extends RuntimeException {
    private final Long bookId;

    public BookNotFoundException(Long bookId) {
        this.bookId = bookId;
    }
}
