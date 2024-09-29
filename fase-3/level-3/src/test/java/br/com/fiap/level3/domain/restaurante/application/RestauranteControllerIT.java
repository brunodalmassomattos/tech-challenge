package br.com.fiap.level3.domain.restaurante.application;

import br.com.fiap.level3.domain.restaurante.core.model.endereco.Endereco;
import br.com.fiap.level3.domain.restaurante.core.model.restaurante.Restaurante;
import br.com.fiap.level3.domain.restaurante.core.model.tiporestaurante.TipoRestaurante;
import br.com.fiap.level3.domain.restaurante.infrastructure.RestauranteDatabaseAdapter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RestauranteControllerIT {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RestauranteDatabaseAdapter restauranteDatabaseAdapter;

    @BeforeEach
    void setUp() {

        restauranteDatabaseAdapter = new RestauranteDatabaseAdapter(jdbcTemplate);

        jdbcTemplate.execute("""
            INSERT INTO tipos_restaurantes (id, descricao) VALUES ('9a4e5a84-9342-46e9-b7dc-92ca7933d1a6', 'Comida Italiana');
            INSERT INTO enderecos (id, cep, rua, numero, bairro, cidade, estado) VALUES 
                ('08fbe866-b5a0-4ed1-a49c-6bde5e22a82f', '12345-678', 'Rua A', '123', 'Bairro B', 'Cidade C', 'Estado D');
            INSERT INTO restaurantes (id, nome, tipo_restaurante_id, horario_funcionamento, capacidade, endereco_id)
                VALUES ('d1e5e21b-0283-47b6-b575-5b695586f76d', 'Restaurante Teste', '9a4e5a84-9342-46e9-b7dc-92ca7933d1a6', '10:00 - 22:00', 100, '08fbe866-b5a0-4ed1-a49c-6bde5e22a82f');
        """);
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute("DELETE FROM restaurantes WHERE id = 'd1e5e21b-0283-47b6-b575-5b695586f76d'");
        jdbcTemplate.execute("DELETE FROM tipos_restaurantes WHERE id = '9a4e5a84-9342-46e9-b7dc-92ca7933d1a6'");
        jdbcTemplate.execute("DELETE FROM enderecos WHERE id = '08fbe866-b5a0-4ed1-a49c-6bde5e22a82f'");
    }

    @Test
    void shouldReturnRestauranteById() {
        // Act
        Optional<Restaurante> restauranteOpt = restauranteDatabaseAdapter.getRestauranteById(UUID.fromString("d1e5e21b-0283-47b6-b575-5b695586f76d"));

        // Assert
        assertTrue(restauranteOpt.isPresent());

        Restaurante restaurante = restauranteOpt.get();
        assertEquals("Restaurante Teste", restaurante.getNome());
        assertEquals("Comida Italiana", restaurante.getTipoRestaurante().getDescricao());
        assertEquals("Rua A", restaurante.getEndereco().getLogradouro());
    }

    @Test
    void shouldReturnRestaurantes() {
        // Act
        List<Restaurante> restaurantes = restauranteDatabaseAdapter.getRestaurantes();

        // Assert
        assertFalse(restaurantes.isEmpty(), "A lista de restaurantes não deve ser vazia");
        Restaurante restaurante = restaurantes.get(0);
        assertNotEquals(null, restaurante);

    }

    @Test
    void shouldReturnRestauranteByTipo() {
        // Act
        var tipoRestaurante = TipoRestaurante.builder().id(UUID.fromString("9a4e5a84-9342-46e9-b7dc-92ca7933d1a6")).build();

        // Act
        List<Restaurante> restauranteOpt = restauranteDatabaseAdapter.getRestaurantesByTipoRestauranteById(tipoRestaurante);

        // Assert
        assertFalse(restauranteOpt.isEmpty(), "A lista de restaurantes não deve ser vazia");
    }

}