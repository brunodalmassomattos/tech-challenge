package br.com.fiap.level3.domain.avaliacao.mocks;

import br.com.fiap.level3.domain.avaliacao.core.domain.model.avaliacao.AvaliacaoDTO;

public class AvaliacaoDTOTestMock {

	public static AvaliacaoDTO getAvaliacaoDTO() {
		return AvaliacaoDTO.builder().id("0c721c3e-e176-4f33-b9f9-5c95b91bd6d4").comentario("Bom!!")
				.nota(10).restauranteId("7ceb223e-0bdf-47a5-bd2d-1cff74715b1e")
				.usuarioId("11033158-f69c-4c4e-a577-70721310983e").build();
		
	}
	
}
