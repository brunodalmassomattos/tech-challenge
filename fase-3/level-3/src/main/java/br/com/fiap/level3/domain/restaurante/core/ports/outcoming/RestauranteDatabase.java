package br.com.fiap.level3.domain.restaurante.core.ports.outcoming;

import br.com.fiap.level3.domain.restaurante.core.domain.model.restaurante.Restaurante;

import java.util.Optional;
import java.util.UUID;

public interface RestauranteDatabase {
    Optional<Restaurante> getRestauranteById(UUID id);
}
