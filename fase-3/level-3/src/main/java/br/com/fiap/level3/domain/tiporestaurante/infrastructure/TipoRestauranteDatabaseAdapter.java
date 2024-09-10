package br.com.fiap.level3.domain.tiporestaurante.infrastructure;

import br.com.fiap.level3.domain.tiporestaurante.core.model.TipoRestaurante;
import br.com.fiap.level3.domain.tiporestaurante.core.ports.outcoming.TipoRestauranteDatabase;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class TipoRestauranteDatabaseAdapter implements TipoRestauranteDatabase {

    private final TipoRestauranteRepository tipoRestauranteRepository;

    @Override
    public void save(TipoRestaurante tipoRestaurante) {
        this.tipoRestauranteRepository.save(tipoRestaurante);
    }

    @Override
    public void delete(UUID id) {
        this.tipoRestauranteRepository.deleteById(id);
    }
}
