package br.com.fiap.level3.domain.exception;

import br.com.fiap.level3.domain.restaurante.core.domain.model.exception.AddRestauranteException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerException {

    private StandardError standardError = new StandardError();

    @ExceptionHandler(AddRestauranteException.class)
    public ResponseEntity<StandardError> entityNotFound(AddRestauranteException exception,
                                                        HttpServletRequest request) {
        this.standardError.setTimeStamp(Instant.now());
        this.standardError.setStatus(HttpStatus.BAD_REQUEST.value());
        this.standardError.setError("Parametros de Entrada incorretos");
        this.standardError.setMessage(exception.getMessage());
        this.standardError.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(this.standardError);
    }

}
