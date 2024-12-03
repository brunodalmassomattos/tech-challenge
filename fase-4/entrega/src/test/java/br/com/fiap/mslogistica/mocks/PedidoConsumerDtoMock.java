package br.com.fiap.entrega.mocks;

import br.com.fiap.entrega.application.dto.PedidoConsumerDto;

import java.util.UUID;

public class PedidoConsumerDtoMock {

    public static PedidoConsumerDto getPedidoConsumerDto() {
        return PedidoConsumerDto.builder()
                       .event("Pagamento confirmado")
                       .notaFiscal("3114553000")
                       .clienteId(UUID.randomUUID().toString())
                       .build();
    }
}
