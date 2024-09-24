package br.com.fiap.level3.domain.tiporestaurante.application;

import br.com.fiap.level3.domain.tiporestaurante.core.model.TipoRestaurante;
import br.com.fiap.level3.domain.tiporestaurante.core.model.TipoRestauranteDTO;
import br.com.fiap.level3.domain.tiporestaurante.core.ports.incoming.AddTipoRestaurante;
import br.com.fiap.level3.domain.tiporestaurante.core.ports.incoming.AlterTipoRestaurante;
import br.com.fiap.level3.domain.tiporestaurante.core.ports.incoming.DeleteTipoRestaurante;
import br.com.fiap.level3.domain.tiporestaurante.core.ports.incoming.FindTipoRestaurante;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TipoRestauranteControllerTest {

    @Mock
    private AddTipoRestaurante addTipoRestaurante;

    @Mock
    private AlterTipoRestaurante alterTipoRestaurante;

    @Mock
    private DeleteTipoRestaurante deleteTipoRestaurante;

    @Mock
    private FindTipoRestaurante findTipoRestaurante;

    private TipoRestauranteController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new TipoRestauranteController(addTipoRestaurante, alterTipoRestaurante, deleteTipoRestaurante, findTipoRestaurante);
    }

    @Test
    public void testAdicionaTipoRestaurante_Sucesso() {
        var id = UUID.randomUUID().toString();
        TipoRestauranteDTO tipoRestauranteDTO = new TipoRestauranteDTO(id, "NOVO TIPO");

        ResponseEntity<String> response = controller.adicionaTipoRestaurante(tipoRestauranteDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(addTipoRestaurante).handle(tipoRestauranteDTO);
    }

    @Nested
    class AlterarTiposRestaurante {
        @Test
        public void testAlteraTipoRestaurante_Sucesso() {
            var id = UUID.randomUUID().toString();
            TipoRestauranteDTO tipoRestauranteDTO = new TipoRestauranteDTO(id, "NOVO TIPO");

            ResponseEntity<String> response = controller.alteraTipoRestaurante(id, tipoRestauranteDTO);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            verify(alterTipoRestaurante).alterData(id, tipoRestauranteDTO);
        }

        @Test
        public void testAlteraTipoRestauranteID_Sucesso() {
            String id = "123";

            ResponseEntity<String> response = controller.alteraTipoRestaurante(id);

            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
            verify(deleteTipoRestaurante).handle(id);
        }
    }

    @Nested
    class BuscarTiposRestaurante {
        @Test
        public void testListaTodosTipoRestaurante_Sucesso() {
            List<TipoRestaurante> tipoRestaurantes = new ArrayList<>();
            tipoRestaurantes.add(new TipoRestaurante(UUID.randomUUID(), "Fast Food"));

            when(findTipoRestaurante.getTipoRestaurantes()).thenReturn(tipoRestaurantes);

            ResponseEntity<List<TipoRestauranteDTO>> response = controller.listaTodosTipoRestaurante();

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertFalse(response.getBody().isEmpty());
        }

        @Test
        public void testListaTodosTipoRestaurante_NenhumTipoEncontrado() {
            when(findTipoRestaurante.getTipoRestaurantes()).thenReturn(new ArrayList<>());

            ResponseEntity<List<TipoRestauranteDTO>> response = controller.listaTodosTipoRestaurante();

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }

        @Test
        public void testBuscarTipoRestaurantePorID_Sucesso() {
            UUID id = UUID.randomUUID();
            TipoRestaurante tipoRestaurante = new TipoRestaurante(id, "Fast Food");

            when(findTipoRestaurante.getTipoRestauranteById(id)).thenReturn(Optional.of(tipoRestaurante));

            ResponseEntity<TipoRestauranteDTO> response = controller.buscarTipoRestaurantePorID(id);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(TipoRestauranteDTO.fromDTO(tipoRestaurante), response.getBody());
        }

        @Test
        public void testBuscarTipoRestaurantePorID_NaoEncontrado() {
            UUID id = UUID.randomUUID();

            when(findTipoRestaurante.getTipoRestauranteById(id)).thenReturn(Optional.empty());

            ResponseEntity<TipoRestauranteDTO> response = controller.buscarTipoRestaurantePorID(id);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }

        @Test
        public void testListaPorTipoRestaurante_Sucesso() {
            String descricao = "Fast Food";
            List<TipoRestaurante> tipoRestaurantes = new ArrayList<>();
            tipoRestaurantes.add(new TipoRestaurante(UUID.randomUUID(), descricao));

            when(findTipoRestaurante.getTipoRestauranteByDescricao(descricao)).thenReturn(tipoRestaurantes);

            ResponseEntity<List<TipoRestauranteDTO>> response = controller.listaPorTipoRestaurante(descricao);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertFalse(response.getBody().isEmpty());
        }

        @Test
        public void testListaPorTipoRestaurante_NenhumTipoEncontrado() {
            String descricao = "Tipo inexistente";

            when(findTipoRestaurante.getTipoRestauranteByDescricao(descricao)).thenReturn(new ArrayList<>());

            ResponseEntity<List<TipoRestauranteDTO>> response = controller.listaPorTipoRestaurante(descricao);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }
    }
}