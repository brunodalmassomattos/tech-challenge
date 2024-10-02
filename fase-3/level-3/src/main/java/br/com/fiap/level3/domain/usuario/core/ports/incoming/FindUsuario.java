package br.com.fiap.level3.domain.usuario.core.ports.incoming;


import br.com.fiap.level3.domain.usuario.core.model.usuario.Usuario;

import java.util.List;

public interface FindUsuario {
    List<Usuario> getUsuarios();
}
