package br.com.fiap.wework.controller.exception;

public class ControllerNotFoundException extends RuntimeException {

    public ControllerNotFoundException(String mensagem) {
        super(mensagem);
    }
}
