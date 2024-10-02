package br.com.fiap.level3.infrastructure;

import br.com.fiap.level3.domain.usuario.core.ports.UsuarioFacade;
import br.com.fiap.level3.domain.usuario.core.ports.incoming.FindUsuario;
import br.com.fiap.level3.domain.usuario.core.ports.outcoming.UsuarioDatabase;
import br.com.fiap.level3.domain.usuario.infrastructure.UsuarioDatabaseAdapter;
import br.com.fiap.level3.domain.usuario.infrastructure.UsuarioRepository;
import org.springframework.context.annotation.Bean;

public class UsuarioDomainConfig {

    @Bean
    public UsuarioDatabase usuarioDatabase(UsuarioRepository usuarioRepository) {
        return new UsuarioDatabaseAdapter(usuarioRepository);
    }

    @Bean
    public FindUsuario findUsuario(UsuarioDatabase usuarioDatabase) {
        return new UsuarioFacade(usuarioDatabase);
    }

}
