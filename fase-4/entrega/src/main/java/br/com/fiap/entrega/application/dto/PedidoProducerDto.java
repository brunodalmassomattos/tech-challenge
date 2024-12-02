package br.com.fiap.entrega.application.dto;

import lombok.Builder;

@Builder
public record PedidoProducerDto(String event,
                                String notaFiscal,
                                String codigoRastreio) {
}
