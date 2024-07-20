package br.com.fiap.newparquimetro.dto.condutor;

import br.com.fiap.newparquimetro.domain.condutor.Condutor;
import br.com.fiap.newparquimetro.domain.condutor.Endereco;
import br.com.fiap.newparquimetro.dto.veiculos.VeiculoDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public record AlteracaoCondutorRequestDTO(
        String nome,
        String cpfCnpj,
        @Schema(type = "string", pattern = "yyyy-MM-dd", example = "2020-01-31")
        String dataNascimento,
        String telefone,
        String idFormaDePagamento,
        EnderecoDTO endereco,
        List<VeiculoDTO> veiculos) implements Serializable {

    public static Date parseDate(String date) throws ParseException {
        if (date == null) {
            return null;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.parse(date);
    }

    public static Condutor prepararRequest(String idCondutor, AlteracaoCondutorRequestDTO condutorRequestDTO) throws ParseException {
        return Condutor.builder()
                .id(idCondutor)
                .nome(condutorRequestDTO.nome())
                .cpfCnpj(condutorRequestDTO.cpfCnpj())
                .dataNascimento(CondutorRequestDTO.parseDate(condutorRequestDTO.dataNascimento()))
                .telefone(condutorRequestDTO.telefone())
                .idFormaPagamento(condutorRequestDTO.idFormaDePagamento())
                .endereco(
                        condutorRequestDTO.endereco == null ? null :
                                Endereco.builder()
                                        .logradouro(condutorRequestDTO.endereco().logradouro())
                                        .numero(condutorRequestDTO.endereco().numero())
                                        .complemento(condutorRequestDTO.endereco().complemento())
                                        .bairro(condutorRequestDTO.endereco().bairro())
                                        .cidade(condutorRequestDTO.endereco().cidade())
                                        .cep(condutorRequestDTO.endereco().cep())
                                        .estado(condutorRequestDTO.endereco().estado())
                                        .build())
                .build();
    }
}
