package br.com.fiap.level3.domain.usuario.infrastructure;

import br.com.fiap.level3.domain.usuario.core.model.usuario.Usuario;
import br.com.fiap.level3.domain.usuario.core.ports.outcoming.UsuarioDatabase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
public class UsuarioDatabaseAdapter implements UsuarioDatabase {

    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return (List<Usuario>) this.usuarioRepository.findAll();
    }

}
