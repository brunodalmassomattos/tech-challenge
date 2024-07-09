package br.com.fiap.newparquimetro.dto;

import br.com.fiap.newparquimetro.domain.condutor.Condutor;
import br.com.fiap.newparquimetro.domain.opcoesDePagamento.OpcoesDePagamento;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpcoesDePagamentoDTO {

    private String id;
    private String tipo;
    private String status;
    private BigDecimal valor;
    private CondutorResponseDTO condutor;

    public static OpcoesDePagamentoDTO toDTO(OpcoesDePagamento opcao) {
        if (opcao == null) {
            return null;
        }
        OpcoesDePagamentoDTO dto = new OpcoesDePagamentoDTO();
        dto.setId(opcao.getId());
        dto.setTipo(opcao.getTipo());
        dto.setStatus(opcao.getStatus());
        dto.setValor(opcao.getValor());
        dto.setCondutor(CondutorResponseDTO.toDTO(opcao.getCondutor(), null));
        return dto;
    }
}

