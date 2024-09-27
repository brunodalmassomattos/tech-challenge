package br.com.fiap.level3.domain.avaliacao.mock;

import java.util.UUID;

import br.com.fiap.level3.domain.reserva.core.model.usuario.Usuario;

public class UsuarioTestMock {
	   public static Usuario getUsuario() {
	        return Usuario.builder()
	                       .id(UUID.fromString("599abd45-3f86-44b6-8837-fc16f130944e"))
	                       .nome("Vitor Joaquim Leandro Lopes")
	                       .build();
	    }
}
