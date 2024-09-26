package br.com.fiap.level3.domain.restaurante.core;

import br.com.fiap.level3.domain.exception.ControllerNotFoundException;
import br.com.fiap.level3.domain.reserva.mocks.RestauranteTestMock;
import br.com.fiap.level3.domain.restaurante.core.model.exception.AddRestauranteException;
import br.com.fiap.level3.domain.restaurante.core.model.restaurante.Restaurante;
import br.com.fiap.level3.domain.restaurante.core.model.tiporestaurante.TipoRestaurante;
import br.com.fiap.level3.domain.restaurante.core.ports.outcoming.RestauranteDatabase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RestauranteFacadeTest {

    private RestauranteFacade restauranteFacade;

    private AutoCloseable openMocks;

    @Mock
    private RestauranteDatabase database;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        restauranteFacade = new RestauranteFacade(database);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class CadastrarRestaurantes {

        @Test
        public void testSave_RestauranteValido() {
            Restaurante restaurante = RestauranteTestMock.buildRestaurante(UUID.randomUUID());
            restauranteFacade.save(restaurante);

            verify(database).save(restaurante);
        }

        @Test
        public void testSave_RestauranteNulo() {
            assertThrows(AddRestauranteException.class, () -> restauranteFacade.save(null));
        }

        @Test
        public void testSave_TipoRestauranteNulo() {
            Restaurante restaurante = new Restaurante();
            restaurante.setTipoRestaurante(null);

            assertThrows(AddRestauranteException.class, () -> restauranteFacade.save(restaurante));
        }

        @Test
        public void testSave_IdTipoRestauranteNulo() {
            Restaurante restaurante = new Restaurante();
            TipoRestaurante tipoRestaurante = new TipoRestaurante();
            restaurante.setTipoRestaurante(tipoRestaurante);

            assertThrows(AddRestauranteException.class, () -> restauranteFacade.save(restaurante));
        }

        @Test
        public void testSave_EnderecoNulo() {
            Restaurante restaurante = RestauranteTestMock.buildRestaurante(UUID.randomUUID());
            restaurante.setEndereco(null);

            assertThrows(AddRestauranteException.class, () -> restauranteFacade.save(restaurante));
        }
    }

    @Nested
    class BuscarRestaurantes {

        @Test
        public void testGetRestaurantes() {
            List<Restaurante> expectedRestaurantes = List.of(
                    RestauranteTestMock.buildRestaurante(UUID.randomUUID()),
                    RestauranteTestMock.buildRestaurante(UUID.randomUUID()));

            when(database.getRestaurantes()).thenReturn(expectedRestaurantes);

            List<Restaurante> actualRestaurantes = restauranteFacade.getRestaurantes();

            assertEquals(expectedRestaurantes, actualRestaurantes);
            verify(database).getRestaurantes();
        }

        @Test
        public void testGetRestaurantesByTipoRestaurante_ById() {
            UUID tipoRestauranteId = UUID.randomUUID();
            TipoRestaurante tipoRestaurante = new TipoRestaurante(tipoRestauranteId, null);
            List<Restaurante> expectedRestaurantes = new ArrayList<>();

            when(database.getRestaurantesByTipoRestauranteById(tipoRestaurante)).thenReturn(expectedRestaurantes);

            List<Restaurante> actualRestaurantes = restauranteFacade.getRestaurantesByTipoRestaurante(tipoRestaurante);

            assertEquals(expectedRestaurantes, actualRestaurantes);
            verify(database).getRestaurantesByTipoRestauranteById(tipoRestaurante);
        }

        @Test
        public void testGetRestaurantesByTipoRestaurante_ByDescricao() {
            String tipoRestauranteDescricao = "Italiano";
            TipoRestaurante tipoRestaurante = new TipoRestaurante(null, tipoRestauranteDescricao);
            List<Restaurante> expectedRestaurantes = new ArrayList<>();

            when(database.getRestaurantesByTipoRestauranteByDescricao(tipoRestaurante)).thenReturn(expectedRestaurantes);

            List<Restaurante> actualRestaurantes = restauranteFacade.getRestaurantesByTipoRestaurante(tipoRestaurante);

            assertEquals(expectedRestaurantes, actualRestaurantes);
            verify(database).getRestaurantesByTipoRestauranteByDescricao(tipoRestaurante);
        }
    }

    @Nested
    class AlterarRestaurantes {

        @Test
        public void testAlterRestaurante_RestauranteExistente() {
            UUID restauranteId = UUID.randomUUID();

            Restaurante restauranteExistente = new Restaurante();
            restauranteExistente.setId(restauranteId);

            Restaurante restauranteNovo = new Restaurante();
            restauranteNovo.setId(restauranteId);
            restauranteNovo.setNome("NOVO NOME");
            restauranteNovo.setCapacidade(23);
            restauranteNovo.setHorarioFuncionamento("NOVO HORARIO");

            when(database.getRestauranteById(restauranteId)).thenReturn(Optional.of(restauranteExistente));

            restauranteFacade.alterRestaurante(restauranteNovo);

            verify(database).update(any(Restaurante.class));
            assertEquals("NOVO NOME", restauranteExistente.getNome());
            assertEquals(23, restauranteExistente.getCapacidade());
            assertEquals("NOVO HORARIO", restauranteExistente.getHorarioFuncionamento());
        }

        @Test
        public void testAlterRestaurante_RestauranteExistenteAlterandoSoNome() {
            UUID restauranteId = UUID.randomUUID();

            Restaurante restauranteExistente = new Restaurante();
            restauranteExistente.setId(restauranteId);

            Restaurante restauranteNovo = new Restaurante();
            restauranteNovo.setId(restauranteId);
            restauranteNovo.setNome("NOVO NOME");

            when(database.getRestauranteById(restauranteId)).thenReturn(Optional.of(restauranteExistente));

            restauranteFacade.alterRestaurante(restauranteNovo);

            verify(database).update(any(Restaurante.class));
            assertEquals("NOVO NOME", restauranteExistente.getNome());
        }

        @Test
        public void testAlterRestaurante_RestauranteExistenteAlterandoSoCapacidade() {
            UUID restauranteId = UUID.randomUUID();

            Restaurante restauranteExistente = new Restaurante();
            restauranteExistente.setId(restauranteId);

            Restaurante restauranteNovo = new Restaurante();
            restauranteNovo.setId(restauranteId);
            restauranteNovo.setCapacidade(12);

            when(database.getRestauranteById(restauranteId)).thenReturn(Optional.of(restauranteExistente));

            restauranteFacade.alterRestaurante(restauranteNovo);

            verify(database).update(any(Restaurante.class));
            assertEquals(12, restauranteExistente.getCapacidade());
        }

        @Test
        public void testAlterRestaurante_RestauranteExistenteAlterandoSoHorario() {
            UUID restauranteId = UUID.randomUUID();

            Restaurante restauranteExistente = new Restaurante();
            restauranteExistente.setId(restauranteId);

            Restaurante restauranteNovo = new Restaurante();
            restauranteNovo.setId(restauranteId);
            restauranteNovo.setHorarioFuncionamento("NOVO HORARIO");

            when(database.getRestauranteById(restauranteId)).thenReturn(Optional.of(restauranteExistente));

            restauranteFacade.alterRestaurante(restauranteNovo);

            verify(database).update(any(Restaurante.class));
            assertEquals("NOVO HORARIO", restauranteExistente.getHorarioFuncionamento());
        }

        @Test
        public void testAlterRestaurante_RestauranteNaoEncontrado() {
            UUID restauranteId = UUID.randomUUID();

            Restaurante restaurante = new Restaurante();
            restaurante.setId(restauranteId);

            when(database.getRestauranteById(restauranteId)).thenReturn(Optional.empty());

            assertThrows(ControllerNotFoundException.class, () -> restauranteFacade.alterRestaurante(restaurante));
        }
    }

    @Nested
    class DeletarRestaurantes {
        @Test
        public void testDeleteRestaurante_RestauranteExistente() {
            UUID restauranteId = UUID.randomUUID();
            Restaurante restauranteExistente = new Restaurante();
            restauranteExistente.setId(restauranteId);

            when(database.getRestauranteById(restauranteId)).thenReturn(Optional.of(restauranteExistente));

            restauranteFacade.deleteRestaurante(restauranteId);

            verify(database).delete(restauranteId);
        }

        @Test
        public void testDeleteRestaurante_RestauranteNaoEncontrado() {
            UUID restauranteId = UUID.randomUUID();

            when(database.getRestauranteById(restauranteId)).thenReturn(Optional.empty());

            assertThrows(ControllerNotFoundException.class, () -> restauranteFacade.deleteRestaurante(restauranteId));
        }
    }
}