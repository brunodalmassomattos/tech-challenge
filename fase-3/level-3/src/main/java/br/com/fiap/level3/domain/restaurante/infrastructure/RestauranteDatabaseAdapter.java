package br.com.fiap.level3.domain.restaurante.infrastructure;

import br.com.fiap.level3.domain.restaurante.core.domain.model.restaurante.Restaurante;
import br.com.fiap.level3.domain.restaurante.core.ports.outcoming.RestauranteDatabase;
import br.com.fiap.level3.domain.restaurante.infrastructure.mapper.RestauranteRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class RestauranteDatabaseAdapter implements RestauranteDatabase {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Restaurante> getRestauranteById(UUID id) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            """
                                      SELECT *
                                        FROM restaurantes r\s
                                        LEFT JOIN tipos_restaurantes tr ON r.tipo_restaurante_id = tr.id
                                        LEFT JOIN enderecos e ON r.endereco_id = e.id\s
                                       WHERE r.id = ?
                                    """,
                            //"select * from restaurantes where id = ?",
                            new RestauranteRowMapper(),
                            id));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }
}
