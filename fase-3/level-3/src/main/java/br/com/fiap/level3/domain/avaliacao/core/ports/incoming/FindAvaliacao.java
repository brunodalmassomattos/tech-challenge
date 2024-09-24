package br.com.fiap.level3.domain.avaliacao.core.ports.incoming;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.com.fiap.level3.domain.avaliacao.core.domain.model.avaliacao.Avaliacao;

public interface FindAvaliacao {

    Optional<Avaliacao> getAvaliacaoById(UUID id);
    
    List<Avaliacao> getAvaliacaoByRestauranteId(UUID restauranteId);
    List<Avaliacao> getAvaliacaoByUsuarioId(UUID usuarioId);
	
}
 