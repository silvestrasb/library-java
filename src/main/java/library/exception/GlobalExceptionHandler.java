package library.exception;

import library.dto.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(BookNotFoundException e) {
        log.warn("Book not found: id = {}", e.getBookId());

        String message = String.format("Book with id = %d was not found", e.getBookId());
        ErrorResponse errorResponse = new ErrorResponse(message, 404);

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
