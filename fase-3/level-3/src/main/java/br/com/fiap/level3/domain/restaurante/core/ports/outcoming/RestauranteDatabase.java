package br.com.fiap.level3.domain.restaurante.core.ports.outcoming;

import br.com.fiap.level3.domain.restaurante.core.model.endereco.Endereco;
import br.com.fiap.level3.domain.restaurante.core.model.restaurante.Restaurante;
import br.com.fiap.level3.domain.restaurante.core.model.tiporestaurante.TipoRestaurante;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RestauranteDatabase {

    Optional<Endereco> getEnderecoById(UUID id);

    Optional<Restaurante> getRestauranteById(UUID id);

    List<Restaurante> getRestaurantes();

    List<Restaurante> getRestaurantesByTipoRestauranteById(TipoRestaurante tipoRestaurante);

    List<Restaurante> getRestaurantesByTipoRestauranteByDescricao(TipoRestaurante tipoRestaurante);

    void save(Restaurante restaurante);

    void update(Restaurante restaurante);

    void delete(UUID id);

    void updateEndereco(Endereco endereco);
}
