package br.com.fiap.level3.domain.exception;

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
