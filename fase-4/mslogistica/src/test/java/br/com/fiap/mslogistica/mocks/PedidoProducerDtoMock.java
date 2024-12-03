package br.com.fiap.mslogistica.mocks;

import br.com.fiap.mslogistica.application.dto.PedidoProducerDto;

public class PedidoProducerDtoMock {

    public static PedidoProducerDto getPedidoProducerDto() {
        return PedidoProducerDto.builder()
                       .event("Rastreio")
                       .notaFiscal("3114553000")
                       .codigoRastreio("ENT2024112501380610266960344023584573")
                       .build();
    }
}
