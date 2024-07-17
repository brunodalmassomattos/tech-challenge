package br.com.fiap.newparquimetro.dto.opcaopagamentos;

import br.com.fiap.newparquimetro.domain.opcoesDePagamento.OpcoesDePagamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpcoesDePagamentoListDTO {

    private String id;
    private String status;
    private String dataPagamento;

    public static Object OpcoesDePagamentoListDTO(OpcoesDePagamento opcao) {
        if (opcao == null) {
            return null;
        }

        OpcoesDePagamentoListDTO dto = new OpcoesDePagamentoListDTO();
        dto.setId(opcao.getId());
        dto.setStatus(opcao.getStatus());
        dto.setDataPagamento(opcao.getDataPagamento());
        return dto;
    }
}

