package br.com.fiap.newparquimetro.dto.formapagamento;

import br.com.fiap.newparquimetro.domain.formapagamento.FormaPagamento;

import java.util.List;
import java.util.stream.Collectors;

public record FormaPagamentoResponseDTO(String id, String tipoDescricao) {

    public static FormaPagamentoResponseDTO toDTO(FormaPagamento formaPagamento) {
        return new FormaPagamentoResponseDTO(formaPagamento.getId(), formaPagamento.getTipo());
    }

    public static List<FormaPagamentoResponseDTO> toDTO(List<FormaPagamento> listaFormaPagamento) {
        return listaFormaPagamento.stream().map(FormaPagamentoResponseDTO::toDTO).collect(Collectors.toList());
    }
}
