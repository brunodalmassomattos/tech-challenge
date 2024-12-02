package br.com.fiap.entrega.application.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerException {

    private final StandardError standardError = new StandardError();

    @ExceptionHandler(ControllerNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(ControllerNotFoundException exception, HttpServletRequest request) {
        this.standardError.setTimeStamp(Instant.now());
        this.standardError.setStatus(HttpStatus.NOT_FOUND.value());
        this.standardError.setError("Entity not found");
        this.standardError.setMessage(exception.getMessage());
        this.standardError.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(this.standardError);
    }
}
