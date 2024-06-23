package br.com.fiap.newparquimetro.dto;

import br.com.fiap.newparquimetro.domain.condutor.Condutor;
import br.com.fiap.newparquimetro.domain.condutor.Endereco;
import br.com.fiap.newparquimetro.domain.condutor.FormaPagamento;
import br.com.fiap.newparquimetro.domain.condutor.Veiculo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public record CondutorResponseDTO(
        String id,
        String nome,
        String cpfCnpj,
        String dataNascimento,
        String telefone,
        FormaPagamento formaPagamento,
        Endereco endereco,
        List<Veiculo> veiculo) implements Serializable {

    public static String parseDate(Date data) {
        if (data == null) {
            return null;
        }

        return new SimpleDateFormat("yyyy-MM-dd").format(data);
    }

    public static CondutorResponseDTO toDTO(Condutor condutor, FormaPagamento formaPagamento) {
        return new CondutorResponseDTO(
                condutor.getId(),
                condutor.getNome(),
                condutor.getCpfCnpj(),
                CondutorResponseDTO.parseDate(condutor.getDataNascimento()),
                condutor.getTelefone(),
                formaPagamento,
                condutor.getEndereco(),
                condutor.getVeiculos());
    }
}
