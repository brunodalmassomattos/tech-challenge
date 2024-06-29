package br.com.fiap.wework.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public record UsuarioRequestDTO(
        @NotBlank(message = "O nome é obrigatorio.")
        String nome,
        @NotBlank(message = "O CPFCNPJ é obrigatorio.")
        String cpfCnpj,
        String dataNascimento,
        String dataAbertura,
        @NotBlank(message = "O email é obrigatorio.")
        @Email(message = "email invalido.")
        String email,
        @NotBlank(message = "A senha é obrigatoria.")
        String senha,
        @NotBlank(message = "O perfil é obrigatorio.")
        String perfil) implements Serializable {

    public static Date parseDate(String date) throws ParseException {
        if (date == null) {
            return null;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.parse(date);
    }
}
