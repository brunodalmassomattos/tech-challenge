package br.com.fiap.newparquimetro.dto;

import br.com.fiap.newparquimetro.domain.opcoesDePagamento.OpcoesDePagamento;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OpcoesDePagamentoDTO {

    private String id;
    private String tipo;
    private String status;
    private BigDecimal valor;
    private String condutor;
    public OpcoesDePagamentoDTO() {
    }

    public OpcoesDePagamentoDTO(OpcoesDePagamento opcao) {
        this.id = opcao.getId();
        this.tipo = opcao.getTipo();
        this.status = opcao.getStatus();
        this.valor = opcao.getValor();
        this.condutor = (opcao.getCondutor() != null) ? opcao.getCondutor().getId() : null;
    }

    public static OpcoesDePagamentoDTO toDTO(OpcoesDePagamento opcao) {
        if (opcao == null) {
            return null;
        }
        OpcoesDePagamentoDTO dto = new OpcoesDePagamentoDTO();
        dto.setId(opcao.getId());
        dto.setTipo(opcao.getTipo());
        dto.setStatus(opcao.getStatus());
        dto.setValor(opcao.getValor());
        dto.setCondutor(opcao.getCondutor() != null ? opcao.getCondutor().getId() : null);
        return dto;
    }
}

