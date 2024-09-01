package br.com.fiap.level3.domain.restaurante.infrastructure;

import br.com.fiap.level3.domain.restaurante.core.domain.model.restaurante.Restaurante;
import br.com.fiap.level3.domain.restaurante.core.ports.outcoming.RestauranteDatabase;
import br.com.fiap.level3.domain.restaurante.infrastructure.mapper.RestauranteRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

@RequiredArgsConstructor
public class RestauranteDatabaseAdapter implements RestauranteDatabase {

    private static final String INSERT_ENDERECO = """
                    INSERT INTO enderecos 
                        (rua, numero, bairro, cidade, estado, cep) 
                    VALUES 
                        (?, ?, ?, ?, ?, ?)
                """;

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Restaurante> getRestauranteById(UUID id) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            """
                                      SELECT *
                                        FROM restaurantes r
                                        LEFT JOIN tipos_restaurantes tr ON r.tipo_restaurante_id = tr.id
                                        LEFT JOIN enderecos e ON r.endereco_id = e.id\s
                                       WHERE r.id = ?
                                    """,
                            new RestauranteRowMapper(),
                            id));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Restaurante> getRestaurantes() {
        try {
            return this.jdbcTemplate.query(
                    """
                            SELECT *
                              FROM restaurantes r
                              LEFT JOIN tipos_restaurantes tr ON r.tipo_restaurante_id = tr.id
                              LEFT JOIN enderecos e ON r.endereco_id = e.id\s
                            """,
                    new RestauranteRowMapper());
        } catch (DataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void save(Restaurante restaurante) {
        Map<String, Object> enderecoId = addEndereco(restaurante);
        addRestaurante(restaurante, enderecoId);
    }

    private Map<String, Object> addEndereco(Restaurante restaurante) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        this.jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps = connection.prepareStatement(INSERT_ENDERECO, new String[] { "id" });
                        ps.setString(1, restaurante.getEndereco().getLogradouro());
                        ps.setString(2, restaurante.getEndereco().getNumero());
                        ps.setString(3, restaurante.getEndereco().getBairro());
                        ps.setString(4, restaurante.getEndereco().getCidade());
                        ps.setString(5, restaurante.getEndereco().getEstado());
                        ps.setString(6, restaurante.getEndereco().getCep());
                        return ps;
                    }
                }, generatedKeyHolder);
        return generatedKeyHolder.getKeyList().get(0);
    }

    private void addRestaurante(Restaurante restaurante, Map<String, Object> enderecoId) {
        try {
            this.jdbcTemplate.update(
                    """
                        INSERT INTO restaurantes 
                            (nome, tipo_restaurante_id, horario_funcionamento, capacidade, endereco_id) 
                        VALUES
                            (?, ?, ?, ?, ?)
                    """,
                    restaurante.getNome(),
                    restaurante.getTipoRestaurante().getId(),
                    restaurante.getHorarioFuncionamento(),
                    restaurante.getCapacidade(),
                    enderecoId.get("id"));
        } catch (DataAccessException e) {
            System.out.println();
        }
    }
}