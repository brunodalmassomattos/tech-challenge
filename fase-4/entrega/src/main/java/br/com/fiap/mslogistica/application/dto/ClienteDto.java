package br.com.fiap.entrega.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
