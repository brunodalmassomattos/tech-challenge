package br.com.fiap.level3.domain.tiporestaurante.core.ports.outcoming;

import br.com.fiap.level3.domain.tiporestaurante.core.model.TipoRestaurante;

public interface TipoRestauranteDatabase {

    void save(TipoRestaurante tipoRestaurante);
}
