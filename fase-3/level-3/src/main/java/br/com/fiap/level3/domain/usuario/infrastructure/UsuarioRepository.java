package br.com.fiap.level3.domain.usuario.infrastructure;

import br.com.fiap.level3.domain.usuario.core.model.usuario.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, UUID> {
}
