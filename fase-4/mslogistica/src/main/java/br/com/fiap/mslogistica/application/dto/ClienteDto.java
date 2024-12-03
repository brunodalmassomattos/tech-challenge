package br.com.fiap.mslogistica.application.dto;

import lombok.Builder;

import java.util.Date;
import java.util.UUID;

@Builder
public record ClienteDto(UUID id,
                         String nome,
                         EnderecoEntregaDto endereco,
                         String cpf,
                         Date dataNascimento,
                         String telefone,
                         String email,
                         EnderecoEntregaDto enderecoEntrega) {
}
