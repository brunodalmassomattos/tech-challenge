package br.com.fiap.level3.infrastructure;

import br.com.fiap.level3.domain.restaurante.core.EnderecoFacade;
import br.com.fiap.level3.domain.restaurante.core.RestauranteFacade;
import br.com.fiap.level3.domain.restaurante.core.ports.incoming.*;
import br.com.fiap.level3.domain.restaurante.core.ports.outcoming.RestauranteDatabase;
import br.com.fiap.level3.domain.restaurante.infrastructure.RestauranteDatabaseAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RestauranteDomainConfig {

    @Bean
    public RestauranteDatabase restauranteDatabase(JdbcTemplate jdbcTemplate) {
        return new RestauranteDatabaseAdapter(jdbcTemplate);
    }

    @Bean
    @Qualifier("FindRestaurante")
    public FindRestaurante findRestaurante(RestauranteDatabase database) {
        return new RestauranteFacade(database);
    }

    @Bean
    @Qualifier("AddRestaurante")
    public AddRestaurante addRestaurante(RestauranteDatabase database) {
        return new RestauranteFacade(database);
    }

    @Bean
    @Qualifier("AlterRestaurante")
    public AlterRestaurante alterRestaurante(RestauranteDatabase database) {
        return new RestauranteFacade(database);
    }

    @Bean
    @Qualifier("DeleteRestaurante")
    public DeleteRestaurante deleteRestaurante(RestauranteDatabase database) {
        return new RestauranteFacade(database);
    }

    @Bean
    @Qualifier("AlterEndereco")
    public AlterEndereco alterEndereco(RestauranteDatabase database) {
        return new EnderecoFacade(database);
    }
}
