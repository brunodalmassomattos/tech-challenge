package br.com.fiap.newparquimetro.dto;

import br.com.fiap.newparquimetro.domain.condutor.FormaPagamento;

public record FormaPagamentoResponseDTO(String id, String tipoDescricao) {
    public static FormaPagamentoResponseDTO toDTO(FormaPagamento formaPagamento) {
        return new FormaPagamentoResponseDTO(formaPagamento.getId(), formaPagamento.getTipo());
    }
}
