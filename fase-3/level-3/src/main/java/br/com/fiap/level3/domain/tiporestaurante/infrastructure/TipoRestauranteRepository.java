package br.com.fiap.level3.domain.tiporestaurante.infrastructure;

import br.com.fiap.level3.domain.tiporestaurante.core.model.TipoRestaurante;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoRestauranteRepository extends CrudRepository<TipoRestaurante, Long> {
}
