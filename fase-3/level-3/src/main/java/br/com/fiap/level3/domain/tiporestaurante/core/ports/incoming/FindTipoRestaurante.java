package br.com.fiap.level3.domain.tiporestaurante.core.ports.incoming;

import br.com.fiap.level3.domain.restaurante.core.model.restaurante.Restaurante;
import br.com.fiap.level3.domain.tiporestaurante.core.model.TipoRestaurante;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FindTipoRestaurante {
    Optional<TipoRestaurante> getTipoRestauranteById(UUID id);

    List<TipoRestaurante> getTipoRestauranteByDescricao(String nome);
    List<TipoRestaurante> getTipoRestaurantes();
}
