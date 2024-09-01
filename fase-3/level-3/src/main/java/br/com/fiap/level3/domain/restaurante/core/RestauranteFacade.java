package br.com.fiap.level3.domain.restaurante.core;

import br.com.fiap.level3.domain.restaurante.core.domain.model.exception.AddRestauranteException;
import br.com.fiap.level3.domain.restaurante.core.domain.model.restaurante.Restaurante;
import br.com.fiap.level3.domain.restaurante.core.ports.incoming.AddRestaurante;
import br.com.fiap.level3.domain.restaurante.core.ports.incoming.FindRestaurante;
import br.com.fiap.level3.domain.restaurante.core.ports.outcoming.RestauranteDatabase;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RestauranteFacade implements FindRestaurante, AddRestaurante {

    private RestauranteDatabase database;

    public RestauranteFacade(RestauranteDatabase database) {
        this.database = database;
    }

    @Override
    public Optional<Restaurante> getRestauranteById(UUID id) {
        return this.database.getRestauranteById(id);
    }

    @Override
    public List<Restaurante> getRestaurantes() {
        return this.database.getRestaurantes();
    }

    @Override
    public void save(Restaurante restaurante) {
        if (restaurante == null) {
            throw new AddRestauranteException("Restaurante é obrigatorio!");
        }

        if (restaurante.getTipoRestaurante() == null || restaurante.getId() == null) {
            throw new AddRestauranteException("Dados do Tipo de Restaurante Invalidos!");
        }

        if (restaurante.getEndereco() == null) {
            throw new AddRestauranteException("Endereço é obrigatorio");
        }

        this.database.save(restaurante);
    }
}
