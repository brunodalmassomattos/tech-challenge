package br.com.fiap.level3.domain.usuario.application;

import br.com.fiap.level3.domain.usuario.core.model.usuario.Usuario;
import br.com.fiap.level3.domain.usuario.core.model.usuario.UsuarioDTO;
import br.com.fiap.level3.domain.usuario.core.ports.incoming.FindUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UsuarioControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testListaTodosUsuarios() throws Exception {
        mockMvc.perform(get("/v1/usuario"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Cauã Carlos Santos"));
    }

}