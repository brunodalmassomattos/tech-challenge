package br.com.fiap.level3.infrastructure;

import br.com.fiap.level3.domain.tiporestaurante.core.ports.TipoRestauranteFacade;
import br.com.fiap.level3.domain.tiporestaurante.core.ports.incoming.AddTipoRestaurante;
import br.com.fiap.level3.domain.tiporestaurante.core.ports.incoming.AlterTipoRestaurante;
import br.com.fiap.level3.domain.tiporestaurante.core.ports.incoming.DeleteTipoRestaurante;
import br.com.fiap.level3.domain.tiporestaurante.core.ports.outcoming.TipoRestauranteDatabase;
import br.com.fiap.level3.domain.tiporestaurante.infrastructure.TipoRestauranteDatabaseAdapter;
import br.com.fiap.level3.domain.tiporestaurante.infrastructure.TipoRestauranteRepository;
import org.springframework.context.annotation.Bean;

public class TipoRestauranteDomainConfig {

    @Bean
    public TipoRestauranteDatabase tipoRestauranteDatabase(TipoRestauranteRepository tipoRestauranteRepository) {
        return new TipoRestauranteDatabaseAdapter(tipoRestauranteRepository);
    }

    @Bean
    public AddTipoRestaurante addTipoRestaurante(TipoRestauranteDatabase tipoRestauranteDatabase) {
        return new TipoRestauranteFacade(tipoRestauranteDatabase);
    }

    @Bean
    public AlterTipoRestaurante alterTipoRestaurante(TipoRestauranteDatabase tipoRestauranteDatabase) {
        return new TipoRestauranteFacade(tipoRestauranteDatabase);
    }

    @Bean
    public DeleteTipoRestaurante deleteTipoRestaurante(TipoRestauranteDatabase tipoRestauranteDatabase) {
        return new TipoRestauranteFacade(tipoRestauranteDatabase);
    }
}
