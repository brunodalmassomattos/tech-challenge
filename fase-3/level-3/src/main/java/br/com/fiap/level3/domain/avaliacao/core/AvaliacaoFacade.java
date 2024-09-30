package br.com.fiap.level3.domain.avaliacao.core;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.com.fiap.level3.domain.exception.ControllerNotFoundException;
import br.com.fiap.level3.domain.reserva.core.model.usuario.Usuario;
import br.com.fiap.level3.domain.reserva.core.ports.outgoing.ReservaDatabase;
import br.com.fiap.level3.domain.restaurante.core.model.exception.AddRestauranteException;
import br.com.fiap.level3.domain.restaurante.core.model.restaurante.Restaurante;
import lombok.AllArgsConstructor;
import br.com.fiap.level3.domain.avaliacao.core.domain.model.avaliacao.Avaliacao;
import br.com.fiap.level3.domain.avaliacao.core.domain.model.exception.AddAvaliacaoException;
import br.com.fiap.level3.domain.avaliacao.core.ports.incoming.AddAvaliacao;
import br.com.fiap.level3.domain.avaliacao.core.ports.incoming.AlterAvaliacao;
import br.com.fiap.level3.domain.avaliacao.core.ports.incoming.FindAvaliacao;
import br.com.fiap.level3.domain.avaliacao.core.ports.outcoming.AvaliacaoDatabase;

@AllArgsConstructor
public class AvaliacaoFacade implements AddAvaliacao, FindAvaliacao, AlterAvaliacao {

	private final AvaliacaoDatabase avaliacaoDatabase;

	@Override
	public void alterAvaliacao(Avaliacao avaliacao) {
		
		validaDados(avaliacao);
		
		Optional<Avaliacao> Existente = this.avaliacaoDatabase.getAvaliacaoById(avaliacao.getId());
		
		if (Existente.isEmpty()) {
			throw new ControllerNotFoundException("Avaliacao não encontrada!");
		}
		
		Avaliacao avaliacaoSalvo = this.getAvaliacaoById(avaliacao.getId()).get();
		avaliacaoSalvo.setNota(avaliacao.getNota() == null ? avaliacaoSalvo.getNota() : avaliacao.getNota());
		avaliacaoSalvo.setComentario(
				avaliacao.getComentario() == null ? avaliacaoSalvo.getComentario() : avaliacao.getComentario());
		this.avaliacaoDatabase.alterAvaliacao(avaliacaoSalvo);

	}

	@Override
	public Optional<Avaliacao> getAvaliacaoById(UUID id) {
		return Optional.ofNullable(this.avaliacaoDatabase.getAvaliacaoById(id)
				.orElseThrow(() -> new ControllerNotFoundException("Avaliacao não encontrada!")));
	}

	@Override
	public List<Optional<Avaliacao>> getAvaliacaoByRestauranteId(UUID restauranteId) {

		List<Optional<Avaliacao>> avaliacoes = this.avaliacaoDatabase.getAvaliacaoByRestauranteId(restauranteId);
		if (avaliacoes.isEmpty()) {
			throw new ControllerNotFoundException("Nenhuma avaliação encontrada para o restaurante com esse id!");
		}

		return avaliacoes;
	}

	@Override
	public List<Optional<Avaliacao>> getAvaliacaoByUsuarioId(UUID usuarioId) {

		List<Optional<Avaliacao>> avaliacoes = this.avaliacaoDatabase.getAvaliacaoByUsuarioId(usuarioId);
		if (avaliacoes.isEmpty()) {
			throw new ControllerNotFoundException("Nenhuma avaliação encontrada para o usuario com esse id!");
		}

		return avaliacoes;

	}

	@Override
	public void save(Avaliacao avaliacao) {

		Optional<Restaurante> restaurante = this.avaliacaoDatabase.getRestauranteById(avaliacao.getRestauranteId());

		if (restaurante.isEmpty()) {
			throw new ControllerNotFoundException("Restaurante não encontrado!");
		}

		Optional<Usuario> usuario = this.avaliacaoDatabase.getUsuarioById(avaliacao.getUsuarioId());

		if (usuario.isEmpty()) {
			throw new ControllerNotFoundException("Usuario não encontrado!");
		}

		validaDados(avaliacao);
		
		this.avaliacaoDatabase.save(avaliacao);
	}
	
	
    private static void validaDados(Avaliacao avaliacao) {
        if(avaliacao.getComentario() != null) {
           	if (avaliacao.getComentario().length() > 280) {
                throw new AddAvaliacaoException("Comentario excedeu o limite de caracteres!");
            }
        } 
        
        if(avaliacao.getNota() != null) {
            if (avaliacao.getNota() < 0 || avaliacao.getNota() > 10) {
                throw new AddAvaliacaoException("Nota apenas de 0 a 10!");
            }
        }

    }

}
