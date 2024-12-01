// domain/infrastructure/handler/GlobalExceptionHandler.java
package br.com.fiap.msgerenciamentocliente.infraestructure.handler;

import br.com.fiap.msgerenciamentocliente.domain.core.entity.Cliente;
import br.com.fiap.msgerenciamentocliente.domain.core.entity.Endereco;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Regex para validações
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern CPF_PATTERN = Pattern.compile("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}");
    private static final Pattern CEP_PATTERN = Pattern.compile("\\d{5}-\\d{3}");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[1-9][0-9]{7,14}$");

    // Classes de Exceção
    public static class BusinessException extends RuntimeException {
        public BusinessException(String message) {
            super(message);
        }
    }

    public static class ResourceNotFoundException extends BusinessException {
        public ResourceNotFoundException(String resource, String identifier) {
            super(String.format("Resource %s with identifier %s not found", resource, identifier));
        }
    }

    public static class ValidationException extends BusinessException {
        private final List<String> validations;

        public ValidationException(String message, List<String> validations) {
            super(message);
            this.validations = validations;
        }

        public List<String> getValidations() {
            return validations;
        }
    }

    // Classe de resposta de erro
    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ErrorResponse {
        private LocalDateTime timestamp;
        private String message;
        private String details;
        private List<String> validations;
        private Integer status;
    }

    // Métodos de Validação
    public void validateCliente(Cliente cliente) {
        List<String> validations = new ArrayList<>();

        // Validações básicas
        if (cliente == null) {
            throw new ValidationException("Cliente não pode ser nulo", List.of("Cliente é obrigatório"));
        }

        // Validação do nome
        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            validations.add("Nome é obrigatório");
        } else if (cliente.getNome().length() < 3) {
            validations.add("Nome deve ter pelo menos 3 caracteres");
        }

        // Validação do CPF
        if (cliente.getCpf() == null || cliente.getCpf().trim().isEmpty()) {
            validations.add("CPF é obrigatório");
        } else if (!CPF_PATTERN.matcher(cliente.getCpf()).matches()) {
            validations.add("CPF deve estar no formato XXX.XXX.XXX-XX");
        }

        // Validação do email
        if (cliente.getEmail() == null || cliente.getEmail().trim().isEmpty()) {
            validations.add("Email é obrigatório");
        } else if (!EMAIL_PATTERN.matcher(cliente.getEmail()).matches()) {
            validations.add("Email inválido");
        }

        // Validação do telefone
        if (cliente.getTelefone() != null && !cliente.getTelefone().isEmpty()
                && !PHONE_PATTERN.matcher(cliente.getTelefone()).matches()) {
            validations.add("Telefone inválido");
        }

        // Validação da data de nascimento
        if (cliente.getDataNascimento() == null) {
            validations.add("Data de nascimento é obrigatória");
        }

        // Validação do endereço principal
        if (cliente.getEndereco() != null) {
            try {
                validateEndereco(cliente.getEndereco());
            } catch (ValidationException e) {
                validations.addAll(e.getValidations());
            }
        }

        // Validação do endereço de entrega (se existir)
        if (cliente.getEnderecoEntrega() != null) {
            try {
                validateEndereco(cliente.getEnderecoEntrega());
            } catch (ValidationException e) {
                validations.addAll(e.getValidations().stream()
                        .map(error -> "Endereço de entrega: " + error)
                        .toList());
            }
        }

        if (!validations.isEmpty()) {
            throw new ValidationException("Erro de validação do cliente", validations);
        }
    }

    public void validateEndereco(Endereco endereco) {
        List<String> validations = new ArrayList<>();

        if (endereco == null) {
            throw new ValidationException("Endereço não pode ser nulo", List.of("Endereço é obrigatório"));
        }

        // Validação da rua
        if (endereco.getRua() == null || endereco.getRua().trim().isEmpty()) {
            validations.add("Rua é obrigatória");
        }

        // Validação do bairro
        if (endereco.getBairro() == null || endereco.getBairro().trim().isEmpty()) {
            validations.add("Bairro é obrigatório");
        }

        // Validação do número
        if (endereco.getNumero() <= 0) {
            validations.add("Número deve ser maior que zero");
        }

        // Validação da cidade
        if (endereco.getCidade() == null || endereco.getCidade().trim().isEmpty()) {
            validations.add("Cidade é obrigatória");
        }

        // Validação do estado
        if (endereco.getEstado() == null || endereco.getEstado().trim().isEmpty()) {
            validations.add("Estado é obrigatório");
        }

        // Validação do CEP
        if (endereco.getCep() == null || endereco.getCep().trim().isEmpty()) {
            validations.add("CEP é obrigatório");
        } else if (!CEP_PATTERN.matcher(endereco.getCep()).matches()) {
            validations.add("CEP deve estar no formato XXXXX-XXX");
        }

        if (!validations.isEmpty()) {
            throw new ValidationException("Erro de validação do endereço", validations);
        }
    }

    public void validateId(UUID id, String resourceName) {
        if (id == null) {
            throw new ValidationException(
                    String.format("ID do %s não pode ser nulo", resourceName),
                    List.of(String.format("ID do %s é obrigatório", resourceName))
            );
        }
    }

    public String formatCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new ValidationException("CPF inválido", List.of("CPF não pode ser vazio"));
        }

        String cpfLimpo = cpf.replaceAll("[^0-9]", "");

        if (cpfLimpo.length() != 11) {
            throw new ValidationException(
                    "CPF inválido",
                    List.of("CPF deve conter exatamente 11 dígitos")
            );
        }

        return cpfLimpo.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

    // Handlers de exceção
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .details(request.getDescription(false))
                .status(HttpStatus.NOT_FOUND.value())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            ValidationException ex, WebRequest request) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .message("Validation error")
                .details(request.getDescription(false))
                .validations(ex.getValidations())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(
            BusinessException ex, WebRequest request) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .details(request.getDescription(false))
                .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex, WebRequest request) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .message("Conflito de dados: registro já existe ou violação de restrição")
                .details(request.getDescription(false))
                .status(HttpStatus.CONFLICT.value())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex, WebRequest request) {

        List<String> validations = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .message("Erro de validação")
                .details(request.getDescription(false))
                .validations(validations)
                .status(HttpStatus.BAD_REQUEST.value())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(
            Exception ex, WebRequest request) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .message("Ocorreu um erro inesperado")
                .details(request.getDescription(false))
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}