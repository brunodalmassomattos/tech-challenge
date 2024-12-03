package br.com.fiap.mslogistica.mocks;

import br.com.fiap.mslogistica.application.dto.PedidoConsumerDto;

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
