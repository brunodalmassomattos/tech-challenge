package br.com.fiap.mslogistica.application.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record EnderecoEntregaDto(
        UUID id,
        String estado,
        String  cidade,
        String bairro,
        String rua,
        String numero,
        String cep
) {
}
