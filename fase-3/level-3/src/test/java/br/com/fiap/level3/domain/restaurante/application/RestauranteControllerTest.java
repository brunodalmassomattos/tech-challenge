package br.com.fiap.level3.domain.restaurante.application;

import br.com.fiap.level3.domain.restaurante.core.model.endereco.Endereco;
import br.com.fiap.level3.domain.restaurante.core.model.endereco.EnderecoDTO;
import br.com.fiap.level3.domain.restaurante.core.model.restaurante.Restaurante;
import br.com.fiap.level3.domain.restaurante.core.model.restaurante.RestauranteDTO;
import br.com.fiap.level3.domain.restaurante.core.model.tiporestaurante.TipoRestaurante;
import br.com.fiap.level3.domain.restaurante.core.model.tiporestaurante.TipoRestauranteDTO;
import br.com.fiap.level3.domain.restaurante.core.ports.incoming.AddRestaurante;
import br.com.fiap.level3.domain.restaurante.core.ports.incoming.AlterRestaurante;
import br.com.fiap.level3.domain.restaurante.core.ports.incoming.DeleteRestaurante;
import br.com.fiap.level3.domain.restaurante.core.ports.incoming.FindRestaurante;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);

        RestauranteController restauranteController = new RestauranteController(
                findRestaurante,
                addRestaurante,
                alterRestaurante,
                deleteRestaurante);

        mockMvc = MockMvcBuilders.standaloneSetup(restauranteController)
//                .setControllerAdvice(new GlobalExceptionHandler())
//                .addFilter((request, response, chain) -> {
//                    response.setCharacterEncoding("UTF-8");
//                    chain.doFilter(request, response);
//                }, "/*")
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
                        .content(asJsonString(buildRestauranteDTO()))
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
                        .content(asJsonString(buildRestauranteDTO()))
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
            when(findRestaurante.getRestaurantes()).thenReturn(buildRestaurantes());

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
        void deveBuscarRestaurantePorId() throws Exception {
            var id = UUID.fromString("37ae4e3c-bdf9-4390-915e-220d5d3348ec");

            when(findRestaurante.getRestauranteById(id)).thenReturn(Optional.ofNullable(buildRestaurante(id)));

            mockMvc.perform(get("/v1/restaurantes/{id}", id).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

            verify(findRestaurante, times(1)).getRestauranteById(any(UUID.class));
        }

        @Test
        void deveRetornarVazioQuandoIdErradoBuscarRestaurantePorId() throws Exception {
            var id = UUID.fromString("37ae4e3c-bdf9-4390-915e-220d5d3348ed");

            when(findRestaurante.getRestauranteById(id)).thenReturn(Optional.empty());

            mockMvc.perform(get("/v1/restaurantes/{id}", id).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());

            verify(findRestaurante, times(1)).getRestauranteById(any(UUID.class));
        }

        @Test
        void deveBuscarRestaurantePorNome() throws Exception {
            var nome = "TESTE NOME";
            when(findRestaurante.getRestauranteByNome(nome)).thenReturn(buildRestaurantes());

            mockMvc.perform(
                    get("/v1/restaurantes/nome")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("nome", nome))
                .andExpect(status().isOk());

            verify(findRestaurante, times(1)).getRestauranteByNome(anyString());
        }

        @Test
        void deveRetornarVazioQuandoNomeErradoRestaurantePorNome() throws Exception {
            var nome = "TESTE";
            when(findRestaurante.getRestauranteByNome(nome)).thenReturn(new ArrayList<>());

            mockMvc.perform(
                    get("/v1/restaurantes/nome")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("nome", nome))
                .andExpect(status().isNotFound());

            verify(findRestaurante, times(1)).getRestauranteByNome(anyString());
        }

        @Test
        void devebuscarRestaurantePorTipo() throws Exception {
            var id = "9cbf57e1-52b0-41a3-8fb7-ccc102658bbd";
            var descricao = "TESTE DESCRICAO";

            when(findRestaurante.getRestaurantesByTipoRestaurante(any(TipoRestaurante.class))).thenReturn(buildRestaurantes());

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

    private List<Restaurante> buildRestaurantes() {
        return List.of(buildRestaurante(null));
    }

    private Restaurante buildRestaurante(final UUID id) {
        return Restaurante.builder()
                .id(id == null ? UUID.fromString("37ae4e3c-bdf9-4390-915e-220d5d3348ec") : id)
                .nome("TESTE NOME")
                .horarioFuncionamento("TESTE HORARIO FUNCIONAMENTO")
                .capacidade(100)
                .status(true)
                .tipoRestaurante(buildTipoRestaurante())
                .endereco(
                        Endereco.builder()
                                .id(UUID.fromString("f592e67c-ba51-4207-9bf5-954393f04365"))
                                .cep("12345678")
                                .logradouro("TESTE RUA")
                                .numero("TESTE NUMERO")
                                .bairro("TESTE BAIRRO")
                                .cidade("TESTE CIDADE")
                                .estado("TE")
                                .build())
                .build();
    }

    private static TipoRestaurante buildTipoRestaurante() {
        return TipoRestaurante.builder()
                .id(UUID.fromString("9cbf57e1-52b0-41a3-8fb7-ccc102658bbd"))
                .descricao("TESTE DESCRICAO")
                .build();
    }

    private RestauranteDTO buildRestauranteDTO() {
        return new RestauranteDTO(
                null,
                "TESTE RESTAURANTE",
                "TESTE HORARIO",
                1,
                true,
                new TipoRestauranteDTO(
                        UUID.randomUUID().toString(),
                        "TESTE DESCRICAO"
                ),
                new EnderecoDTO(
                        null,
                        "Teste CEP",
                        "TESTE RUA",
                        "TESTE NUMERO",
                        "TESTE BAIRRO",
                        "TESTE CIDADE",
                        "TESTE ESTADO")
        );
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}