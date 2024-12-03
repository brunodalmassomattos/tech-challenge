package br.com.fiap.mslogistica.application.dto;

import lombok.Builder;

@Builder
public record PedidoProducerDto(String event,
                                String notaFiscal,
                                String codigoRastreio) {
}
