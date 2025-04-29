package pl.ludwikowski.libraryV1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.ludwikowski.libraryV1.config.BorrowCartException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BorrowCartException.class)
    public ResponseEntity<String> handleBorrowCartException(BorrowCartException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
