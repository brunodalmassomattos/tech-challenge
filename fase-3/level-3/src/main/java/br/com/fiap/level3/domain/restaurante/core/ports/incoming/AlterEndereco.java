package br.com.fiap.level3.domain.restaurante.core.ports.incoming;

import br.com.fiap.level3.domain.restaurante.core.domain.model.endereco.Endereco;
import java.util.UUID;

public interface AlterEndereco {
    void alterEndereco(UUID idRestaurante, Endereco endereco);
}
