package br.com.fiap.level3.domain.restaurante.core.ports.incoming;

import br.com.fiap.level3.domain.restaurante.core.model.restaurante.Restaurante;

public interface AlterRestaurante {
    void alterRestaurante(Restaurante restaurante);
}
