package br.com.fiap.newparquimetro.dto;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public record CondutorRequestDTO(
        @NotBlank(message = "O nome é obrigatorio.")
        String nome,
        @NotBlank(message = "O CPF/CNPJ é obrigatorio.")
        String cpfCnpj,
        String dataNascimento,
        String telefone,
        String idFormaDePagamento,
        EnderecoDTO endereco,
        List<VeiculoDTO> veiculos
        ) implements Serializable {

    public static Date parseDate(String date) throws ParseException {
        if (date == null) {
            return null;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.parse(date);
    }
}
