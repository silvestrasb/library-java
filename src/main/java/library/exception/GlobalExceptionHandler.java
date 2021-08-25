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

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(ResourceNotFoundException e) {
        log.warn("Resource not found: id = {}", e.getId());

        String message = String.format("Resource with id = %d was not found", e.getId());
        ErrorResponse errorResponse = new ErrorResponse(message, HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handle(EmailAlreadyExistsException e) {
        log.warn("User with such username already exists = {}", e.getEmail());

        String message = String.format("User with email = %s already exists.", e.getEmail());
        ErrorResponse errorResponse = new ErrorResponse(message, HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(UserNotFoundException e) {
        log.warn("User not found: username {}", e.getUsername());

        String message = String.format("User with username = %s was not found", e.getUsername());
        ErrorResponse errorResponse = new ErrorResponse(message, HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}
