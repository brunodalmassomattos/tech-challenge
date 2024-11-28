package br.com.fiap.level3.domain.restaurante.core.ports.incoming;

import br.com.fiap.level3.domain.restaurante.core.domain.model.restaurante.Restaurante;

import java.util.Optional;
import java.util.UUID;

public interface FindByIdRestaurante {
    Optional<Restaurante> getRestauranteById(UUID id);
}