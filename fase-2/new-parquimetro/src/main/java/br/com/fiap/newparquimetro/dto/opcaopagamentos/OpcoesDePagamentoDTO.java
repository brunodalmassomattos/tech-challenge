package br.com.fiap.newparquimetro.dto.opcaopagamentos;

import br.com.fiap.newparquimetro.domain.opcoesDePagamento.OpcoesDePagamento;
import br.com.fiap.newparquimetro.dto.condutor.CondutorResponseDTO;
import br.com.fiap.newparquimetro.dto.recibos.ReciboResponseDTO;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpcoesDePagamentoDTO {

    private String id;
    private String idTempo;
    private String dataPagamento;
    private String status;
    private BigDecimal valor;
    private CondutorResponseDTO condutor;
    private ReciboResponseDTO recibo;

    public static OpcoesDePagamentoDTO toDTO(OpcoesDePagamento opcao) {
        if (opcao == null) {
            return null;
        }
        OpcoesDePagamentoDTO dto = new OpcoesDePagamentoDTO();
        dto.setId(opcao.getId());
        dto.setIdTempo(opcao.getIdTempo());
        dto.setDataPagamento(opcao.getDataPagamento());
        dto.setStatus(opcao.getStatus());
        dto.setValor(opcao.getValor());
        dto.setCondutor(CondutorResponseDTO.toDTO(opcao.getCondutor(), null));
        dto.setRecibo(ReciboResponseDTO.builder().build());
        return dto;
    }
}

