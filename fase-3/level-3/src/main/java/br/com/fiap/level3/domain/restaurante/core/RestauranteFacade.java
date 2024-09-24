package br.com.fiap.level3.domain.restaurante.core;

import br.com.fiap.level3.domain.exception.ControllerNotFoundException;
import br.com.fiap.level3.domain.restaurante.core.model.exception.AddRestauranteException;
import br.com.fiap.level3.domain.restaurante.core.model.restaurante.Restaurante;
import br.com.fiap.level3.domain.restaurante.core.model.tiporestaurante.TipoRestaurante;
import br.com.fiap.level3.domain.restaurante.core.ports.incoming.AddRestaurante;
import br.com.fiap.level3.domain.restaurante.core.ports.incoming.AlterRestaurante;
import br.com.fiap.level3.domain.restaurante.core.ports.incoming.DeleteRestaurante;
import br.com.fiap.level3.domain.restaurante.core.ports.incoming.FindRestaurante;
import br.com.fiap.level3.domain.restaurante.core.ports.outcoming.RestauranteDatabase;

import java.util.List;
import java.util.UUID;

public class RestauranteFacade implements FindRestaurante, AddRestaurante, AlterRestaurante, DeleteRestaurante {

    private final RestauranteDatabase database;

    public RestauranteFacade(RestauranteDatabase database) {
        this.database = database;
    }

    private Restaurante getRestauranteById(UUID id) {
        return this.database.getRestauranteById(id)
                .orElseThrow(() -> new ControllerNotFoundException("Restaurante não encontrado!"));
    }

    @Override
    public List<Restaurante> getRestaurantes() {
        return this.database.getRestaurantes();
    }

    @Override
    public List<Restaurante> getRestaurantesByTipoRestaurante(TipoRestaurante tipoRestaurante) {
        if (tipoRestaurante.getId() != null) {
            return this.database.getRestaurantesByTipoRestauranteById(tipoRestaurante);
        }

        return this.database.getRestaurantesByTipoRestauranteByDescricao(tipoRestaurante);
    }

    @Override
    public void save(Restaurante restaurante) {
        validaDados(restaurante);
        this.database.save(restaurante);
    }

    @Override
    public void alterRestaurante(Restaurante restaurante) {
        var restautanteSalvo = this.getRestauranteById(restaurante.getId());
        restautanteSalvo.setNome(restaurante.getNome() == null ? restautanteSalvo.getNome() : restaurante.getNome());
        restautanteSalvo.setHorarioFuncionamento(restaurante.getHorarioFuncionamento() == null ? restautanteSalvo.getHorarioFuncionamento() : restaurante.getHorarioFuncionamento());
        restautanteSalvo.setCapacidade(restaurante.getCapacidade() == 0 ? restautanteSalvo.getCapacidade() : restaurante.getCapacidade());

        this.database.update(restautanteSalvo);
    }

    @Override
    public void deleteRestaurante(UUID id) {
        var restautanteSalvo = this.getRestauranteById(id);
        this.database.delete(restautanteSalvo.getId());
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
}
