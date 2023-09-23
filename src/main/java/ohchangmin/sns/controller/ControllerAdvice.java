package ohchangmin.sns.controller;

import ohchangmin.sns.exception.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionMessage> CustomException(CustomException e) {
        ExceptionMessage message = new ExceptionMessage(e.getStatusCode(), e.getMessage());

        return ResponseEntity
                .status(message.statusCode())
                .body(message);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ExceptionMessage> BindException(BindException e) {
        ExceptionMessage message = new ExceptionMessage(
                BAD_REQUEST.value(),
                requireNonNull(e.getFieldError()).getDefaultMessage());

        return ResponseEntity
                .status(message.statusCode())
                .body(message);
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<ExceptionMessage> MissingServletRequestPartException() {
        ExceptionMessage message = new ExceptionMessage(BAD_REQUEST.value(), "파일을 추가해야합니다.");

        return ResponseEntity
                .status(message.statusCode())
                .body(message);
    }

    private record ExceptionMessage(int statusCode, String message) {
    }
}
