package br.com.fiap.level3.domain.tiporestaurante.infrastructure;

import br.com.fiap.level3.domain.tiporestaurante.core.model.TipoRestaurante;
import br.com.fiap.level3.domain.tiporestaurante.core.ports.outcoming.TipoRestauranteDatabase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    @Override
    public TipoRestaurante findById(UUID id) {
        return this.tipoRestauranteRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoRestaurante> findAll() {
        return (List<TipoRestaurante>) this.tipoRestauranteRepository.findAll();
    }

    @Override
    public List<TipoRestaurante> findByDescricao(String descricao) {
        return this.tipoRestauranteRepository.findByDescricao(descricao);
    }
}
