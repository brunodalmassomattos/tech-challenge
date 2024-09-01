package br.com.fiap.level3.domain.restaurante.core.ports.incoming;

import br.com.fiap.level3.domain.restaurante.core.domain.model.restaurante.Restaurante;

public interface AddRestaurante {
    void save(Restaurante restaurante);
}
