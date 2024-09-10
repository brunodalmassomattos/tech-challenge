package br.com.fiap.level3.domain.tiporestaurante.core.ports.incoming;

import br.com.fiap.level3.domain.tiporestaurante.core.model.TipoRestauranteDTO;

public interface AddTipoRestaurante {
    void handle(TipoRestauranteDTO tipoRestauranteDTO);
}
