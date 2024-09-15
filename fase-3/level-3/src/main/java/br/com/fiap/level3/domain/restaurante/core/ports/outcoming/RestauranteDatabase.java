package br.com.fiap.level3.domain.restaurante.core.ports.outcoming;

import br.com.fiap.level3.domain.restaurante.core.domain.model.endereco.Endereco;
import br.com.fiap.level3.domain.restaurante.core.domain.model.restaurante.Restaurante;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RestauranteDatabase {
    Optional<Restaurante> getRestauranteById(UUID id);
    Optional<Endereco> getEnderecoById(UUID id);
    List<Restaurante> getRestauranteByNome(String nome);
    List<Restaurante> getRestaurantes();

    void save(Restaurante restaurante);
    void update(Restaurante restaurante);
    void updateEndereco(Endereco endereco);
}