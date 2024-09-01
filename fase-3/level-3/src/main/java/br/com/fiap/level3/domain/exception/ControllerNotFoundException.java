package br.com.fiap.level3.domain.exception;

public class ControllerNotFoundException extends RuntimeException {

    public ControllerNotFoundException(String mensagem) {
        super(mensagem);
    }
}
