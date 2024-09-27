package br.com.fiap.level3.domain.avaliacao.core.ports.outcoming;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.com.fiap.level3.domain.avaliacao.core.domain.model.avaliacao.Avaliacao;
import br.com.fiap.level3.domain.reserva.core.model.usuario.Usuario;
import br.com.fiap.level3.domain.restaurante.core.model.restaurante.Restaurante;

public interface AvaliacaoDatabase {

	Optional<Avaliacao> getAvaliacaoById(UUID id);

	List<Optional<Avaliacao>> getAvaliacaoByRestauranteId(UUID restauranteId);

	List<Optional<Avaliacao>> getAvaliacaoByUsuarioId(UUID usuarioId);

	void alterAvaliacao(Avaliacao avaliacao);

	void save(Avaliacao avaliacao);
	
    Optional<Restaurante> getRestauranteById(UUID id);

    Optional<Usuario> getUsuarioById(UUID id);
    
}
