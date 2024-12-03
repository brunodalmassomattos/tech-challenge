package br.com.fiap.entrega.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record PedidoConsumerDto(@JsonProperty("event") String event,
                                @JsonProperty("notaFiscal") String notaFiscal,
                                @JsonProperty("idCliente") String clienteId) {
}
