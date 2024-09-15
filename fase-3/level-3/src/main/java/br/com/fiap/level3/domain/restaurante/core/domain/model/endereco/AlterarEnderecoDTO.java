package br.com.fiap.level3.domain.restaurante.core.domain.model.endereco;

import java.util.UUID;

public record AlterarEnderecoDTO(
        String cep,
        String logradouro,
        String numero,
        String bairro,
        String cidade,
        String estado) {

    public static Endereco toEndereco(String id, AlterarEnderecoDTO dto) {
        return new Endereco(
                UUID.fromString(id),
                dto.cep,
                dto.logradouro,
                dto.numero,
                dto.bairro,
                dto.cidade,
                dto.estado);
    }
}

