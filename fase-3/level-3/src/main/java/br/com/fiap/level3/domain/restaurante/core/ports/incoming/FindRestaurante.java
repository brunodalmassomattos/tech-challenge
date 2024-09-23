package br.com.fiap.level3.domain.restaurante.core.ports.incoming;

import br.com.fiap.level3.domain.restaurante.core.model.restaurante.Restaurante;
import br.com.fiap.level3.domain.restaurante.core.model.tiporestaurante.TipoRestaurante;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FindRestaurante {
    Optional<Restaurante> getRestauranteById(UUID id);

    List<Restaurante> getRestauranteByNome(String nome);
    List<Restaurante> getRestaurantes();
    List<Restaurante> getRestaurantesByTipoRestaurante(TipoRestaurante tipoRestaurante);
}
