package br.com.fiap.level3.domain.restaurante.core.ports.incoming;

import br.com.fiap.level3.domain.restaurante.core.model.endereco.Endereco;

import java.util.UUID;

public interface AlterEndereco {
    void alterEndereco(UUID idRestaurante, UUID idEndereco, Endereco endereco);
}