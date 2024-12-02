package br.com.fiap.msgerenciamentocliente.domain.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
    private UUID id;
    private String nome;
    private Endereco endereco;
    private String cpf;
    private Date dataNascimento;
    private String telefone;
    private String email;
    private Endereco enderecoEntrega;
}