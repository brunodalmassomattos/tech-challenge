package br.com.fiap.level3.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidateMessage {
    private String field;
    private String message;
}
