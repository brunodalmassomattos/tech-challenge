package br.com.fiap.level3.domain.avaliacao.infrastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import br.com.fiap.level3.domain.avaliacao.infrastructure.mapper.AvaliacaoRowMapper;
import br.com.fiap.level3.domain.avaliacao.core.domain.model.avaliacao.Avaliacao;
import br.com.fiap.level3.domain.avaliacao.core.ports.outcoming.AvaliacaoDatabase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AvaliacaoDatabaseAdapter implements AvaliacaoDatabase{

    private final JdbcTemplate jdbcTemplate;

	@Override
	public Optional<Avaliacao> getAvaliacaoById(UUID id) {
		try {
			return Optional.ofNullable(
					jdbcTemplate.queryForObject("SELECT * FROM avaliacoes WHERE id = ?", new AvaliacaoRowMapper(), id));
		} catch (DataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public List<Avaliacao> getAvaliacaoByRestauranteId(UUID restauranteId) {

		try {
			return jdbcTemplate.query("SELECT * FROM avaliacoes WHERE restaurante_id = ?", new AvaliacaoRowMapper(),
					restauranteId);
		} catch (DataAccessException e) {
			return new ArrayList<>();
		}
	}
	
	@Override
	public List<Avaliacao> getAvaliacaoByUsuarioId(UUID usuarioId) {
		try {
			return jdbcTemplate.query("SELECT * FROM avaliacoes WHERE usuario_id = ?", new AvaliacaoRowMapper(),
					usuarioId);
		} catch (DataAccessException e) {
			return new ArrayList<>();
		}
	}

	@Override
	public void alterAvaliacao(Avaliacao avaliacao) {

		try {
			this.jdbcTemplate.update("UPDATE avaliacoes SET nota = ?, comentario = ? WHERE id = ?",avaliacao.getNota(),avaliacao.getComentario(),avaliacao.getId());
		}catch (DataAccessException e) {
            System.out.println(e);
		}
	}

    @Override
    public void save(Avaliacao avaliacao) {
        addRestaurante(avaliacao);
    }

	private void addRestaurante(Avaliacao avaliacao) {
		try {
			this.jdbcTemplate.update(
					"insert into avaliacoes(nota,comentario,restaurante_id,usuario_id) values(?, ?, ?, ?)",
					avaliacao.getNota(), avaliacao.getComentario(), avaliacao.getRestauranteId(),
					avaliacao.getUsuarioId());
		} catch (DataAccessException e) {
			System.out.println(e);
		}
	}
	
}
