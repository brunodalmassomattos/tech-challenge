package br.com.fiap.wework.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record UsuarioAlteracaoRequestDTO(
        @NotBlank(message = "O nome é obrigatorio.")
        String nome,
        @NotBlank(message = "O CPFCNPJ é obrigatorio.")
        String cpfCnpj,
        String dataNascimento,
        String dataAbertura,
        @NotBlank(message = "O email é obrigatorio.")
        @Email(message = "email invalido.")
        String email,
        String senha,
        @NotBlank(message = "O perfil é obrigatorio.")
        String perfil,
        String status) implements Serializable {
}
