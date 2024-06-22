package br.com.fiap.newparquimetro.dto;

import br.com.fiap.newparquimetro.domain.condutor.Condutor;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public record CondutorResponseDTO(
        String id,
        String nome,
        String cpfCnpj,
        String dataNascimento) implements Serializable {

    public static String parseDate(Date data) {
        if (data == null) {
            return null;
        }

        return new SimpleDateFormat("yyyy-MM-dd").format(data);
    }

    public static CondutorResponseDTO toDTO(Condutor save) {
        return new CondutorResponseDTO(
                save.getId(),
                save.getNome(),
                save.getCpfCnpj(),
                CondutorResponseDTO.parseDate(save.getDataNascimento()));
    }
}
