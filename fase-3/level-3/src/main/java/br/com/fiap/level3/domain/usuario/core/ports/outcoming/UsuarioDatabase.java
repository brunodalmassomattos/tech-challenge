package br.com.fiap.level3.domain.usuario.core.ports.outcoming;

import br.com.fiap.level3.domain.usuario.core.model.usuario.Usuario;

import java.util.List;

public interface UsuarioDatabase {
    List<Usuario> findAll();
}
