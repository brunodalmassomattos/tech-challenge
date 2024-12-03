package br.com.fiap.mslogistica.application.gateway;

import br.com.fiap.mslogistica.application.dto.ClienteDto;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface ClienteGateway {

    @Gateway(requestChannel = "apiClienteChannel", requestTimeout = 5000)
    ClienteDto obterClientePorId(String clienteId);
}
