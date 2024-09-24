package br.com.fiap.level3.domain.restaurante.application;

import br.com.fiap.level3.domain.reserva.mocks.RestauranteTestMock;
import br.com.fiap.level3.domain.restaurante.core.model.restaurante.Restaurante;
import br.com.fiap.level3.domain.restaurante.core.model.restaurante.RestauranteDTO;
import br.com.fiap.level3.domain.restaurante.core.ports.incoming.AddRestaurante;
import br.com.fiap.level3.domain.restaurante.core.ports.incoming.FindRestaurante;
import br.com.fiap.level3.domain.restaurante.core.ports.incoming.AlterRestaurante;
import br.com.fiap.level3.domain.restaurante.core.ports.incoming.DeleteRestaurante;
import br.com.fiap.level3.domain.restaurante.core.ports.incoming.AlterEndereco;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RestauranteControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FindRestaurante findRestaurante;

    @Autowired
    private AddRestaurante addRestaurante;

    @Autowired
    private AlterRestaurante alterRestaurante;

    @Autowired
    private DeleteRestaurante deleteRestaurante;

    @Autowired
    private AlterEndereco alterEndereco;

    private Restaurante restaurante;

    @BeforeEach
    public void setUp() {
        restaurante = RestauranteTestMock.buildRestaurante(UUID.randomUUID());
        addRestaurante.save(restaurante);
    }

    @Test
    public void testAdicionarRestaurante() throws Exception {
        RestauranteDTO dto = RestauranteTestMock.buildRestauranteDTO();

        mockMvc.perform(post("/v1/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"Novo Restaurante\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Novo Restaurante cadastrado"));

        verify(addRestaurante, times(1)).save(any(Restaurante.class));
    }

}