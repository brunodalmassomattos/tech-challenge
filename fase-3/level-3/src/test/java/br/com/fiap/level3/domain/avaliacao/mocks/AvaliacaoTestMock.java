package br.com.fiap.level3.domain.avaliacao.mocks;

import java.util.UUID;

import br.com.fiap.level3.domain.avaliacao.core.domain.model.avaliacao.Avaliacao;

public class AvaliacaoTestMock {

	public static Avaliacao getAvaliacao() {
		return Avaliacao.builder().id(UUID.fromString("0c721c3e-e176-4f33-b9f9-5c95b91bd6d4")).comentario("Bom!!")
				.nota(7).restauranteId(UUID.fromString("7ceb223e-0bdf-47a5-bd2d-1cff74715b1e"))
				.usuarioId(UUID.fromString("11033158-f69c-4c4e-a577-70721310983e")).build();
	}

	public static Avaliacao getAvaliacaoNotaMaiorQue10() {
		return Avaliacao.builder().id(UUID.fromString("0c721c3e-e176-4f33-b9f9-5c95b91bd6d4")).comentario("Bom!!")
				.nota(11).restauranteId(UUID.fromString("7ceb223e-0bdf-47a5-bd2d-1cff74715b1e"))
				.usuarioId(UUID.fromString("11033158-f69c-4c4e-a577-70721310983e")).build();
	}

	public static Avaliacao getAvaliacaoNotaMenorQue0() {
		return Avaliacao.builder().id(UUID.fromString("0c721c3e-e176-4f33-b9f9-5c95b91bd6d4")).comentario("Bom!!")
				.nota(-11).restauranteId(UUID.fromString("7ceb223e-0bdf-47a5-bd2d-1cff74715b1e"))
				.usuarioId(UUID.fromString("11033158-f69c-4c4e-a577-70721310983e")).build();
	}

	public static Avaliacao getAvaliacaoRestauranteInexistente() {
		return Avaliacao.builder().id(UUID.fromString("0c721c3e-e176-4f33-b9f9-5c95b91bd6d4")).comentario("Bom!!")
				.nota(7).restauranteId(UUID.fromString("7ceb223e-0bdf-47a5-bd2d-1cff74715b1a"))
				.usuarioId(UUID.fromString("11033158-f69c-4c4e-a577-70721310983e")).build();
	}

	public static Avaliacao getAvaliacaoUsuarioInexistente() {
		return Avaliacao.builder().id(UUID.fromString("0c721c3e-e176-4f33-b9f9-5c95b91bd6d4")).comentario("Bom!!")
				.nota(7).restauranteId(UUID.fromString("7ceb223e-0bdf-47a5-bd2d-1cff74715b1e"))
				.usuarioId(UUID.fromString("11033158-f69c-4c4e-a577-70721310983a")).build();
	}

	public static Avaliacao getAvaliacaoNumeroCaracteresForaDoLimite() {
		return Avaliacao.builder().id(UUID.fromString("0c721c3e-e176-4f33-b9f9-5c95b91bd6d4")).comentario(
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
				.nota(7).restauranteId(UUID.fromString("7ceb223e-0bdf-47a5-bd2d-1cff74715b1e"))
				.usuarioId(UUID.fromString("11033158-f69c-4c4e-a577-70721310983e")).build();
	}

}
