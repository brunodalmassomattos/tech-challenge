package br.com.fiap.level3.domain.usuario.application;

import br.com.fiap.level3.domain.usuario.core.model.usuario.Usuario;
import br.com.fiap.level3.domain.usuario.core.model.usuario.UsuarioDTO;
import br.com.fiap.level3.domain.usuario.core.ports.incoming.FindUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

class UsuarioControllerTest {

    @Mock
    private FindUsuario findUsuario;

    private UsuarioController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new UsuarioController(findUsuario);
    }

    @Test
    public void testListaTodosUsuarios_Sucesso() {
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(Usuario.builder()
                .id(UUID.randomUUID())
                .nome("RETORNO NOME")
                .build());

        when(findUsuario.getUsuarios()).thenReturn(usuarios);

        ResponseEntity<List<UsuarioDTO>> response = controller.listaUsuarios();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }

}