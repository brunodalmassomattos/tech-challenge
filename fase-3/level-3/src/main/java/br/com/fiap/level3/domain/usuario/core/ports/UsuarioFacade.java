package br.com.fiap.level3.domain.usuario.core.ports;

import br.com.fiap.level3.domain.usuario.core.model.usuario.Usuario;
import br.com.fiap.level3.domain.usuario.core.ports.incoming.FindUsuario;
import br.com.fiap.level3.domain.usuario.core.ports.outcoming.UsuarioDatabase;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class UsuarioFacade implements FindUsuario {

    private final UsuarioDatabase usuarioDatabase;

    @Override
    public List<Usuario> getUsuarios() {
        return usuarioDatabase.findAll();
    }
}
