package br.com.fiap.level3.infrastructure;

import br.com.fiap.level3.domain.restaurante.core.RestauranteFacade;
import br.com.fiap.level3.domain.restaurante.core.ports.incoming.FindByIdRestaurante;
import br.com.fiap.level3.domain.restaurante.core.ports.outcoming.RestauranteDatabase;
import br.com.fiap.level3.domain.restaurante.infrastructure.RestauranteDatabaseAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

public class RestauranteConfig {

    @Bean
    public RestauranteDatabase restauranteDatabase(JdbcTemplate jdbcTemplate) {
        return new RestauranteDatabaseAdapter(jdbcTemplate);
    }

    @Bean
    @Qualifier("FindByIdRestaurante")
    public FindByIdRestaurante findByIdRestaurante(RestauranteDatabase database) {
        return new RestauranteFacade(database);
    }
}
