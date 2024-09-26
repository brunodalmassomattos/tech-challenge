package br.com.fiap.level3;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.jdbc.core.JdbcTemplate;

@RequiredArgsConstructor
public class DatabaseHelper {

    private final JdbcTemplate jdbcTemplate;

    public String getUsuario(String nomeUsuario) {
        return jdbcTemplate.queryForObject(
                "SELECT id FROM usuarios WHERE nome = ?",
                String.class,
                nomeUsuario);
    }

    public String getRestauranteId(String nomeRestaurante) {
        return jdbcTemplate.queryForObject(
                "SELECT id FROM restaurantes WHERE nome = ?",
                String.class,
                nomeRestaurante
        );
    }

    public String getReservaId(String usuarioId){
        String teste = jdbcTemplate.queryForObject(
                "SELECT id FROM reservas WHERE usuario_id = ?",
                String.class,
                usuarioId);

        return teste;
    }

    public String getReservaId2(String data){
        String teste = jdbcTemplate.queryForObject(
                "SELECT id FROM reservas WHERE data = ?",
                String.class,
                data);

        return teste;
    }
}
