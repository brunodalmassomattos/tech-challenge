package br.com.fiap.level3.domain.avaliacao.core.domain.model.avaliacao;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record AvaliacaoDTO(String id, Integer nota, String comentario, String restauranteId, String usuarioId) {

	public static AvaliacaoDTO fromAvaliacao(Avaliacao avaliacao) {
		return new AvaliacaoDTO(avaliacao.getId().toString(), avaliacao.getNota(), avaliacao.getComentario(),
				avaliacao.getRestauranteId().toString(), avaliacao.getUsuarioId().toString());
	}
	
    public static List<AvaliacaoDTO> fromAvaliacoes(List<Avaliacao> avaliacoes) {
        return avaliacoes
                .stream()
                .map(AvaliacaoDTO::fromAvaliacao)
                .collect(Collectors.toList());
     }
	
	public static Avaliacao toAvaliacao(String id, AvaliacaoDTO avaliacao) {
		return new Avaliacao((id != null) ? UUID.fromString(id) : null, avaliacao.nota, avaliacao.comentario,
				UUID.fromString(avaliacao.restauranteId()), UUID.fromString(avaliacao.usuarioId));
	}

}
