package br.com.fiap.funcionalidadeDeCargaDeProdutos.infrastructure.handler;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Trata exceções genéricas de runtime.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex, WebRequest request) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    /**
     * Trata exceções quando uma entidade não é encontrada.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        String mensagem = "Recurso não encontrado: " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagem);
    }

    /**
     * Trata erros de validação em métodos anotados com @Valid.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String campo = ((FieldError) error).getField();
            String mensagem = error.getDefaultMessage();
            erros.put(campo, mensagem);
        });
        return ResponseEntity.badRequest().body(erros);
    }

    /**
     * Trata exceções de tipo de argumento inválido em métodos de controller.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        String mensagem = String.format("O parâmetro '%s' recebeu o valor '%s', que é de um tipo inválido. Corrija e tente novamente.",
                ex.getName(), ex.getValue());
        return ResponseEntity.badRequest().body(mensagem);
    }

    /**
     * Trata todas as outras exceções não capturadas por handlers específicos.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAllExceptions(Exception ex, WebRequest request) {
        String mensagem = "Ocorreu um erro interno no servidor. Por favor, tente novamente mais tarde.";
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensagem);
    }
}
