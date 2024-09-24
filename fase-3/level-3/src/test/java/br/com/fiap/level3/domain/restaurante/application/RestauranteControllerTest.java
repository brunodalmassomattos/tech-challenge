package br.com.fiap.level3.domain.restaurante.application;

import br.com.fiap.level3.domain.reserva.mocks.RestauranteTestMock;
import br.com.fiap.level3.domain.restaurante.core.model.endereco.Endereco;
import br.com.fiap.level3.domain.restaurante.core.model.endereco.EnderecoDTO;
import br.com.fiap.level3.domain.restaurante.core.model.restaurante.Restaurante;
import br.com.fiap.level3.domain.restaurante.core.model.restaurante.RestauranteDTO;
import br.com.fiap.level3.domain.restaurante.core.model.tiporestaurante.TipoRestaurante;
import br.com.fiap.level3.domain.restaurante.core.model.tiporestaurante.TipoRestauranteDTO;
import br.com.fiap.level3.domain.restaurante.core.ports.incoming.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestauranteControllerTest {

    private MockMvc mockMvc;
    private AutoCloseable openMocks;

    @Mock
    private FindRestaurante findRestaurante;

    @Mock
    private AddRestaurante addRestaurante;

    @Mock
    private AlterRestaurante alterRestaurante;

    @Mock
    private DeleteRestaurante deleteRestaurante;

    @Mock
    private AlterEndereco alterEndereco;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);

        RestauranteController restauranteController = new RestauranteController(
                findRestaurante,
                addRestaurante,
                alterRestaurante,
                deleteRestaurante,
                alterEndereco);

        mockMvc = MockMvcBuilders.standaloneSetup(restauranteController)
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class CadastrarRestaurantes {

        @Test
        void deveAdicionarRestaurante() throws Exception {
            doNothing().when(addRestaurante).save(any(Restaurante.class));

            mockMvc.perform(
                    post("/v1/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(RestauranteTestMock.buildRestauranteDTO()))
            ).andExpect(status().isCreated());

            verify(addRestaurante, times(1)).save(any(Restaurante.class));
        }
    }

    @Nested
    class AlterarRestaurantes {

        @Test
        void deveAlteraRestaurante() throws Exception {
            var idRestaurante = UUID.fromString("37ae4e3c-bdf9-4390-915e-220d5d3348ec");

            doNothing().when(alterRestaurante).alterRestaurante(any(Restaurante.class));

            mockMvc.perform(
                    patch("/v1/restaurantes/{id}", idRestaurante)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(RestauranteTestMock.buildRestauranteDTO()))
            ).andExpect(status().isAccepted());

            verify(alterRestaurante, times(1)).alterRestaurante(any(Restaurante.class));
        }
    }

    @Nested
    class DeletarRestaurantes {

        @Test
        void deveDeletaRestaurante() throws Exception {
            var idRestaurante = UUID.fromString("37ae4e3c-bdf9-4390-915e-220d5d3348ec");

            doNothing().when(deleteRestaurante).deleteRestaurante(any(UUID.class));

            mockMvc.perform(
                    delete("/v1/restaurantes/{id}", idRestaurante)
                        .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk());

            verify(deleteRestaurante, times(1)).deleteRestaurante(any(UUID.class));
        }
    }

    @Nested
    class BuscarRestaurantes {

        @Test
        void deveBuscarRestaurante() throws Exception {
            when(findRestaurante.getRestaurantes()).thenReturn(RestauranteTestMock.buildRestaurantes());

            mockMvc.perform(get("/v1/restaurantes").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

            verify(findRestaurante, times(1)).getRestaurantes();
        }

        @Test
        void deveRetornarVazioQuandoNaoHouverRestauranteCadastradosBuscarRestaurante() throws Exception {
            when(findRestaurante.getRestaurantes()).thenReturn(new ArrayList<>());

            mockMvc.perform(get("/v1/restaurantes").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());

            verify(findRestaurante, times(1)).getRestaurantes();
        }

        @Test
        void devebuscarRestaurantePorTipo() throws Exception {
            var id = "9cbf57e1-52b0-41a3-8fb7-ccc102658bbd";
            var descricao = "TESTE DESCRICAO";

            when(findRestaurante.getRestaurantesByTipoRestaurante(any(TipoRestaurante.class))).thenReturn(RestauranteTestMock.buildRestaurantes());

            mockMvc.perform(
                            get("/v1/restaurantes/tipo-restaurante")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .param("id", id)
                                    .param("descricao", descricao))
                    .andExpect(status().isOk());

            verify(findRestaurante, times(1)).getRestaurantesByTipoRestaurante(any(TipoRestaurante.class));
        }

        @Test
        void deveRetornarVazioQuandoTipoInvalidoBuscarRestaurantePorTipo() throws Exception {
            var id = "9cbf57e1-52b0-41a3-8fb7-ccc102658bbd";
            var descricao = "TESTE";

            when(findRestaurante.getRestaurantesByTipoRestaurante(any(TipoRestaurante.class))).thenReturn(new ArrayList<>());

            mockMvc.perform(
                            get("/v1/restaurantes/tipo-restaurante")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .param("id", id)
                                    .param("descricao", descricao))
                    .andExpect(status().isNotFound());

            verify(findRestaurante, times(1)).getRestaurantesByTipoRestaurante(any(TipoRestaurante.class));
        }
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}