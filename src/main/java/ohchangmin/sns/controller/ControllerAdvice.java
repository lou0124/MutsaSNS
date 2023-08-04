package ohchangmin.sns.controller;

import ohchangmin.sns.exception.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionMessage> ExceptionHandler(CustomException e) {
        ExceptionMessage message = new ExceptionMessage(e.getStatusCode(), e.getMessage());
        return ResponseEntity
                .status(message.statusCode())
                .body(message);
    }

    private record ExceptionMessage(int statusCode, String message) {
    }
}
