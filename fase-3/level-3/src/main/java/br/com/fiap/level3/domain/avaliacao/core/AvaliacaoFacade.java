package br.com.fiap.level3.domain.avaliacao.core;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.com.fiap.level3.domain.exception.ControllerNotFoundException;
import br.com.fiap.level3.domain.avaliacao.core.domain.model.avaliacao.Avaliacao;
import br.com.fiap.level3.domain.avaliacao.core.ports.incoming.AddAvaliacao;
import br.com.fiap.level3.domain.avaliacao.core.ports.incoming.AlterAvaliacao;
import br.com.fiap.level3.domain.avaliacao.core.ports.incoming.FindAvaliacao;
import br.com.fiap.level3.domain.avaliacao.core.ports.outcoming.AvaliacaoDatabase;

public class AvaliacaoFacade implements AddAvaliacao, FindAvaliacao, AlterAvaliacao{

	private final AvaliacaoDatabase avaliacaoDatabase;
	
    public AvaliacaoFacade(AvaliacaoDatabase avaliacaoDatabase) {
        this.avaliacaoDatabase = avaliacaoDatabase;
    }
	
	@Override
	public void alterAvaliacao(Avaliacao avaliacao) {

        Avaliacao avaliacaoSalvo = this.getAvaliacaoById(avaliacao.getId()).get();
        avaliacaoSalvo.setNota(avaliacao.getNota() == null ? avaliacaoSalvo.getNota() : avaliacao.getNota());
        avaliacaoSalvo.setComentario(avaliacao.getComentario() == null ? avaliacaoSalvo.getComentario() : avaliacao.getComentario());
        this.avaliacaoDatabase.alterAvaliacao(avaliacaoSalvo);
        
	}

	@Override
	public Optional<Avaliacao> getAvaliacaoById(UUID id) {
		return Optional.ofNullable(this.avaliacaoDatabase.getAvaliacaoById(id)
				.orElseThrow(() -> new ControllerNotFoundException("Avaliacao não encontrada!")));
	}

	@Override
	public List<Avaliacao> getAvaliacaoByRestauranteId(UUID restauranteId) {
		return this.avaliacaoDatabase.getAvaliacaoByRestauranteId(restauranteId);
	}

	@Override
	public List<Avaliacao> getAvaliacaoByUsuarioId(UUID usuarioId) {
		return this.avaliacaoDatabase.getAvaliacaoByUsuarioId(usuarioId);
	}

	@Override
	public void save(Avaliacao avaliacao) {
		this.avaliacaoDatabase.save(avaliacao);
	}
	
	
}
