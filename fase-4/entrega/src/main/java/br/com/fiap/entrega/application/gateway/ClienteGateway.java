package br.com.fiap.entrega.application.gateway;

import br.com.fiap.entrega.application.dto.ClienteDto;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.GatewayHeader;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.MessageHeaders;

@MessagingGateway
public interface ClienteGateway {

    @Gateway(requestChannel = "apiClienteChannel", requestTimeout = 5000)
    ClienteDto obterClientePorId(String clienteId);
}
