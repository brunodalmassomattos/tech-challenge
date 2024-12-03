package br.com.fiap.mslogistica.application.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record EnderecoClienteDto(UUID id,
                                 String rua,
                                 String bairro,
                                 String complemento,
                                 int numero,
                                 String cidade,
                                 String estado,
                                 String cep) {
}
