package br.com.fiap.level3.domain.restaurante.core;

import br.com.fiap.level3.domain.exception.ControllerNotFoundException;
import br.com.fiap.level3.domain.restaurante.core.domain.model.exception.AddRestauranteException;
import br.com.fiap.level3.domain.restaurante.core.domain.model.restaurante.Restaurante;
import br.com.fiap.level3.domain.restaurante.core.domain.model.tiporestaurante.TipoRestaurante;
import br.com.fiap.level3.domain.restaurante.core.ports.incoming.AddRestaurante;
import br.com.fiap.level3.domain.restaurante.core.ports.incoming.AlterRestaurante;
import br.com.fiap.level3.domain.restaurante.core.ports.incoming.FindRestaurante;
import br.com.fiap.level3.domain.restaurante.core.ports.outcoming.RestauranteDatabase;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RestauranteFacade implements FindRestaurante, AddRestaurante, AlterRestaurante {

    private final RestauranteDatabase database;

    public RestauranteFacade(RestauranteDatabase database) {
        this.database = database;
    }

    @Override
    public Optional<Restaurante> getRestauranteById(UUID id) {
        return Optional.ofNullable(this.database.getRestauranteById(id)
                .orElseThrow(() -> new ControllerNotFoundException("Restaurante não encontrado!")));
    }

    @Override
    public List<Restaurante> getRestauranteByNome(String nome) {
        return this.database.getRestauranteByNome(nome);
    }

    @Override
    public List<Restaurante> getRestaurantes() {
        return this.database.getRestaurantes();
    }

    @Override
    public void save(Restaurante restaurante) {
        validaDados(restaurante);
        this.database.save(restaurante);
    }

    @Override
    public void alterRestaurante(Restaurante restaurante) {
        var restautanteSalvo = this.getRestauranteById(restaurante.getId()).get();
        restautanteSalvo.setNome(restaurante.getNome() == null ? restautanteSalvo.getNome() : restaurante.getNome());
        restautanteSalvo.setHorarioFuncionamento(restaurante.getHorarioFuncionamento() == null ? restautanteSalvo.getHorarioFuncionamento() : restaurante.getHorarioFuncionamento());
        restautanteSalvo.setCapacidade(restaurante.getCapacidade() == 0 ? restautanteSalvo.getCapacidade() : restaurante.getCapacidade());

        this.database.update(restautanteSalvo);
    }

    private static void validaDados(Restaurante restaurante) {
        if (restaurante == null) {
            throw new AddRestauranteException("Restaurante é obrigatorio!");
        }

        if (restaurante.getTipoRestaurante() == null || restaurante.getTipoRestaurante().getId() == null) {
            throw new AddRestauranteException("Dados do Tipo de Restaurante Invalidos!");
        }

        if (restaurante.getEndereco() == null) {
            throw new AddRestauranteException("Endereço é obrigatorio");
        }
    }
    public void alterTipoRestaurante(UUID idRestaurante, TipoRestaurante tipoRestaurante) {
        Optional<Restaurante> restaurante = database.getRestauranteById(idRestaurante);
        if (restaurante.isEmpty()) {
            throw new ControllerNotFoundException("Restaurante não encontrado!");
        }

        restaurante.get().setTipoRestaurante(tipoRestaurante);
        database.updateTipoRestaurante(restaurante.get());
    }
}
