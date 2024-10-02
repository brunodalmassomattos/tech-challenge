package br.com.fiap.level3.domain.usuario.core.ports;

import br.com.fiap.level3.domain.tiporestaurante.core.model.TipoRestaurante;
import br.com.fiap.level3.domain.usuario.core.model.usuario.Usuario;
import br.com.fiap.level3.domain.usuario.core.ports.outcoming.UsuarioDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UsuarioFacadeTest {

    private UsuarioDatabase usuarioDatabase;
    private UsuarioFacade usuarioFacade;

    @BeforeEach
    public void setUp() {
        usuarioDatabase = mock(UsuarioDatabase.class);
        usuarioFacade = new UsuarioFacade(usuarioDatabase);
    }

    @Test
    public void testGetTipoRestaurantes() {
        Usuario usuario = Usuario.builder().id(UUID.randomUUID()).nome("TESTE USUARIO").build();
        Usuario usuario1 = Usuario.builder().id(UUID.randomUUID()).nome("TESTE USUARIO 1").build();

        when(usuarioDatabase.findAll()).thenReturn(List.of(usuario, usuario1));

        List<Usuario> result = usuarioFacade.getUsuarios();

        assertEquals(2, result.size());
        assertEquals("TESTE USUARIO", result.get(0).getNome());
        assertEquals("TESTE USUARIO 1", result.get(1).getNome());
    }

}