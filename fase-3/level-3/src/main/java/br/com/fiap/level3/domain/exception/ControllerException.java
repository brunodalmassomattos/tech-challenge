package br.com.fiap.level3.domain.exception;

import br.com.fiap.level3.domain.restaurante.core.model.exception.AddRestauranteException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerException {

    private StandardError standardError = new StandardError();

    @ExceptionHandler(AddRestauranteException.class)
    public ResponseEntity<StandardError> validation(AddRestauranteException exception,
                                                        HttpServletRequest request) {
        this.standardError.setTimeStamp(Instant.now());
        this.standardError.setStatus(HttpStatus.BAD_REQUEST.value());
        this.standardError.setError("Parametros de Entrada incorretos");
        this.standardError.setMessage(exception.getMessage());
        this.standardError.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(this.standardError);
    }

    @ExceptionHandler(ControllerNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(ControllerNotFoundException exception,
                                                        HttpServletRequest request) {
        this.standardError.setTimeStamp(Instant.now());
        this.standardError.setStatus(HttpStatus.NOT_FOUND.value());
        this.standardError.setError("Entity not found");
        this.standardError.setMessage(exception.getMessage());
        this.standardError.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(this.standardError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validation(MethodArgumentNotValidException exception,
                                                    HttpServletRequest request) {
        var validateError = new ValidateError();

        validateError.setTimeStamp(Instant.now());
        validateError.setStatus(HttpStatus.BAD_REQUEST.value());
        validateError.setError("Entity not found");
        validateError.setMessage(exception.getMessage());
        validateError.setPath(request.getRequestURI());

        exception.getBindingResult().getFieldErrors().forEach(item -> validateError.addMessage(item.getField(), item.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validateError);
    }
}
