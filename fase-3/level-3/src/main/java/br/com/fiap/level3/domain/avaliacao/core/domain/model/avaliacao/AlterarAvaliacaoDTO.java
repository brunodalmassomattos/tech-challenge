package br.com.fiap.level3.domain.avaliacao.core.domain.model.avaliacao;

import java.util.UUID;

import lombok.Builder;

@Builder
public record AlterarAvaliacaoDTO (Integer nota,String comentario ){
		
	public static Avaliacao toAvaliacao(String id, AlterarAvaliacaoDTO alterarAvaliacaoDTO) {
		return new Avaliacao(UUID.fromString(id),alterarAvaliacaoDTO.nota,alterarAvaliacaoDTO.comentario);
	}
	
}
