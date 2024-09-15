package br.com.fiap.level3.domain.tiporestaurante.core.ports.outcoming;

import br.com.fiap.level3.domain.tiporestaurante.core.model.TipoRestaurante;

import java.util.List;
import java.util.UUID;

public interface TipoRestauranteDatabase {

    void save(TipoRestaurante tipoRestaurante);
    void delete(UUID id);

    TipoRestaurante findById(UUID id);
    List<TipoRestaurante> findAll();
    List<TipoRestaurante> findByDescricao(String nome);
}
