package br.com.fiap.wework.controller.exception;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidateError extends StandardError {

    private List<ValidateMessage> messages = new ArrayList<>();

    public void addMessage(final String field, final String message) {
        messages.add(new ValidateMessage(field, message));
    }

}
