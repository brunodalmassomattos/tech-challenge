package br.com.fiap.level3.domain.restaurante.core;

import br.com.fiap.level3.domain.restaurante.core.domain.model.restaurante.Restaurante;
import br.com.fiap.level3.domain.restaurante.core.ports.incoming.FindByIdRestaurante;
import br.com.fiap.level3.domain.restaurante.core.ports.outcoming.RestauranteDatabase;

import java.util.Optional;
import java.util.UUID;

public class RestauranteFacade implements FindByIdRestaurante {

    private RestauranteDatabase database;

    public RestauranteFacade(RestauranteDatabase database) {
        this.database = database;
    }

    @Override
    public Optional<Restaurante> getRestauranteById(UUID id) {
        return this.database.getRestauranteById(id);
    }
}
