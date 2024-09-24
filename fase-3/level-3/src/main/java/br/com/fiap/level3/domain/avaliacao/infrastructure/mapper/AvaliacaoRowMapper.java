package br.com.fiap.level3.domain.avaliacao.infrastructure.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;

import br.com.fiap.level3.domain.avaliacao.core.domain.model.avaliacao.Avaliacao;

public class AvaliacaoRowMapper implements RowMapper<Avaliacao> {

	@Override
	public Avaliacao mapRow(ResultSet rs, int rowNum) throws SQLException {
		return Avaliacao.builder().id(UUID.fromString(rs.getString("id"))).nota(rs.getInt("nota"))
				.comentario(rs.getString("comentario")).restauranteId(UUID.fromString(rs.getString("restaurante_id")))
				.usuarioId(UUID.fromString(rs.getString("usuario_id"))).build();

	}

}
