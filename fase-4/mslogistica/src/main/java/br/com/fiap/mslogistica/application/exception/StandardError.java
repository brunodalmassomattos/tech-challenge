package br.com.fiap.mslogistica.application.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class StandardError {
    private Instant timeStamp;
    private String error;
    private String message;
    private String path;
    private int status;
}
