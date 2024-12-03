package br.com.fiap.mslogistica.mocks;

import br.com.fiap.mslogistica.application.dto.ClienteDto;
import br.com.fiap.mslogistica.application.dto.EnderecoEntregaDto;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

public class ClienteDtoMock {

    public static ClienteDto getClienteDto() {
        return ClienteDto.builder()
                       .id(UUID.randomUUID())
                       .cpf("461.155.600-04")
                       .email("matheus@email.com")
                       .dataNascimento(Date.from(Instant.now()))
                       .nome("Matheus")
                       .telefone("17991566895")
                       .endereco(EnderecoEntregaDto.builder().id(UUID.randomUUID()).cep("15130-023").build())
                       .enderecoEntrega(EnderecoEntregaDto.builder().id(UUID.randomUUID()).cep("15130-023").build())
                       .build();
    }
}
