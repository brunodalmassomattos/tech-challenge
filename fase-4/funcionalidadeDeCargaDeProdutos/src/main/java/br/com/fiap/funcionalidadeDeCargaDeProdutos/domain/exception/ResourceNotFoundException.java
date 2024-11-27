package br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
