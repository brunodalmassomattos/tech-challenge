package br.com.fiap.level3.domain.tiporestaurante.core.ports.outcoming;

import br.com.fiap.level3.domain.tiporestaurante.core.model.TipoRestaurante;

import java.util.UUID;

public interface TipoRestauranteDatabase {

    void save(TipoRestaurante tipoRestaurante);

    void delete(UUID id);
}
