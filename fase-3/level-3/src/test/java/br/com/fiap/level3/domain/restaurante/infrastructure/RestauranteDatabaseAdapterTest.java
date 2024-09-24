package br.com.fiap.level3.domain.restaurante.infrastructure;

import br.com.fiap.level3.domain.reserva.mocks.EnderecoTestMock;
import br.com.fiap.level3.domain.reserva.mocks.RestauranteTestMock;
import br.com.fiap.level3.domain.restaurante.core.model.endereco.Endereco;
import br.com.fiap.level3.domain.restaurante.core.model.restaurante.Restaurante;
import br.com.fiap.level3.domain.restaurante.core.model.tiporestaurante.TipoRestaurante;
import br.com.fiap.level3.domain.restaurante.infrastructure.mapper.RestauranteRowMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.SQLException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class RestauranteDatabaseAdapterTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    private AutoCloseable openMocks;

    private RestauranteDatabaseAdapter adapter;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        adapter = new RestauranteDatabaseAdapter(jdbcTemplate);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class BuscarRestaurantes {

        @Test
        public void testGetRestauranteById_RestauranteEncontrado() throws DataAccessException {
            UUID restauranteId = UUID.randomUUID();
            Restaurante esperado = new Restaurante();
            esperado.setId(restauranteId);

            // Mock do jdbcTemplate para simular a consulta bem-sucedida
            when(jdbcTemplate.queryForObject(any(String.class), any(RowMapper.class), any(Object.class))).thenReturn(esperado);

            // Executa o método
            Optional<Restaurante> optionalRestaurante = adapter.getRestauranteById(restauranteId);

            // Verifica o resultado
            assertTrue(optionalRestaurante.isPresent());
            assertEquals(esperado, optionalRestaurante.get());
        }

        @Test
        public void testGetRestauranteById_RestauranteNaoEncontrado() throws DataAccessException {
            UUID restauranteId = UUID.randomUUID();

            // Mock do jdbcTemplate para simular a consulta sem resultados
            when(jdbcTemplate.queryForObject(any(String.class), any(RowMapper.class), any(Object.class))).thenReturn(null);

            // Executa o método
            Optional<Restaurante> optionalRestaurante = adapter.getRestauranteById(restauranteId);

            // Verifica o resultado
            assertFalse(optionalRestaurante.isPresent());
        }

        @Test
        public void testGetRestauranteById_DataAccessException() throws DataAccessException {
            UUID restauranteId = UUID.randomUUID();

            // Mock do jdbcTemplate para simular uma exceção
            when(jdbcTemplate.queryForObject(any(String.class), any(RowMapper.class), any(Object.class)))
                    .thenThrow(new DataAccessException("Erro na consulta") {
                    });

            // Executa o método
            Optional<Restaurante> optionalRestaurante = adapter.getRestauranteById(restauranteId);

            // Verifica o resultado
            assertFalse(optionalRestaurante.isPresent());

        }

        @Test
        public void testGetRestaurantesByTipoRestauranteById_RestaurantesEncontrados() throws DataAccessException {
            UUID tipoRestauranteId = UUID.randomUUID();
            List<Restaurante> restaurantesEsperados = new ArrayList<>();
            restaurantesEsperados.add(new Restaurante());
            restaurantesEsperados.add(new Restaurante());

            // Mock do jdbcTemplate para simular a consulta com resultados
            when(jdbcTemplate.query(any(String.class), any(RowMapper.class), any(Object.class))).thenReturn(restaurantesEsperados);

            // Executa o método
            List<Restaurante> restaurantes = adapter.getRestaurantesByTipoRestauranteById(new TipoRestaurante(tipoRestauranteId));

            // Verifica o resultado
            assertFalse(restaurantes.isEmpty());
            assertEquals(restaurantesEsperados, restaurantes);
        }

        @Test
        public void testGetRestaurantesByTipoRestauranteById_NenhumRestauranteEncontrado() throws DataAccessException {
            UUID tipoRestauranteId = UUID.randomUUID();

            // Mock do jdbcTemplate para simular a consulta sem resultados
            when(jdbcTemplate.query(any(String.class), any(RowMapper.class), any(Object.class))).thenReturn(new ArrayList<>());

            // Executa o método
            List<Restaurante> restaurantes = adapter.getRestaurantesByTipoRestauranteById(new TipoRestaurante(tipoRestauranteId));

            // Verifica o resultado
            assertTrue(restaurantes.isEmpty());
        }

        @Test
        public void testGetRestaurantesByTipoRestauranteById_DataAccessException() throws DataAccessException {
            // Mock do jdbcTemplate para simular uma exceção
            when(jdbcTemplate.query(any(String.class), any(RowMapper.class), any(Object.class)))
                    .thenThrow(new DataAccessException("Erro na consulta") {
                    });

            // Executa o método
            List<Restaurante> restaurantes = adapter.getRestaurantesByTipoRestauranteById(new TipoRestaurante(UUID.randomUUID()));

            // Verifica o resultado
            assertTrue(restaurantes.isEmpty());
        }

        @Test
        public void testGetRestaurantesByTipoRestauranteByDescricao_RestaurantesEncontrados() throws DataAccessException {
            String tipoRestauranteDescricao = "Comida Italiana";
            var restaurante = RestauranteTestMock.buildRestaurante(UUID.randomUUID());
            restaurante.getTipoRestaurante().setDescricao(tipoRestauranteDescricao);

            List<Restaurante> restaurantesEsperados = List.of(restaurante);

            // Mock do jdbcTemplate para simular a consulta com resultados
            when(jdbcTemplate.query(any(String.class), any(Object[].class), any(RowMapper.class))).thenReturn(restaurantesEsperados);

            // Executa o método
            List<Restaurante> restaurantes = adapter.getRestaurantesByTipoRestauranteByDescricao(new TipoRestaurante(null, tipoRestauranteDescricao));

            // Verifica o resultado
            assertFalse(restaurantes.isEmpty());
            assertEquals(restaurantesEsperados, restaurantes);
        }

        @Test
        public void testGetRestaurantesByTipoRestauranteByDescricao_NenhumRestauranteEncontrado() throws DataAccessException {
            String tipoRestauranteDescricao = "Comida Tailandesa (Rara)";

            // Mock do jdbcTemplate para simular a consulta sem resultados
            when(jdbcTemplate.query(any(String.class), any(Object[].class), any(RowMapper.class))).thenReturn(new ArrayList<>());

            // Executa o método
            List<Restaurante> restaurantes = adapter.getRestaurantesByTipoRestauranteByDescricao(new TipoRestaurante(null, tipoRestauranteDescricao));

            // Verifica o resultado
            assertTrue(restaurantes.isEmpty());
        }

        @Test
        public void testGetRestaurantesByTipoRestauranteByDescricao_DataAccessException() throws DataAccessException {
            String tipoRestauranteDescricao = "Pizza";

            // Mock do jdbcTemplate para simular uma exceção
            when(jdbcTemplate.query(any(String.class), any(Object[].class), any(RowMapper.class)))
                    .thenThrow(new DataAccessException("Erro na consulta") {
                    });

            // Executa o método
            var restaurantes = adapter.getRestaurantesByTipoRestauranteByDescricao(new TipoRestaurante(null, tipoRestauranteDescricao));
            // Verifica o resultado
            assertTrue(restaurantes.isEmpty());
        }

        @Test
        public void testGetRestaurantes_RestaurantesEncontrados() throws DataAccessException {
            List<Restaurante> restaurantesEsperados = List.of(RestauranteTestMock.buildRestaurante(UUID.randomUUID()));

            // Mock do jdbcTemplate para simular a consulta com resultados
            when(jdbcTemplate.query(any(String.class), any(RowMapper.class))).thenReturn(restaurantesEsperados);

            // Executa o método
            List<Restaurante> restaurantes = adapter.getRestaurantes();

            // Verifica o resultado
            assertFalse(restaurantes.isEmpty());
            assertEquals(restaurantesEsperados, restaurantes);
        }

        @Test
        public void testGetRestaurantes_NenhumRestauranteEncontrado() throws DataAccessException {

            // Mock do jdbcTemplate para simular a consulta sem resultados
            when(jdbcTemplate.query(any(String.class), any(RowMapper.class))).thenReturn(new ArrayList<>());

            // Executa o método
            List<Restaurante> restaurantes = adapter.getRestaurantes();

            // Verifica o resultado
            assertTrue(restaurantes.isEmpty());
        }

        @Test
        public void testGetRestaurantes_DataAccessException() throws DataAccessException {

            // Mock do jdbcTemplate para simular uma exceção
            when(jdbcTemplate.query(any(String.class), any(RowMapper.class))).thenThrow(new DataAccessException("Erro na consulta") {
            });

            // Executa o método
            var restaurantes = adapter.getRestaurantes();

            // Verifica o resultado
            assertTrue(restaurantes.isEmpty());
        }
    }

    @Nested
    class BuscarEnderecos {

        @Test
        public void testGetEnderecoById_EnderecoEncontrado() throws SQLException, DataAccessException {
            UUID enderecoId = UUID.randomUUID();
            var enderecoEsperado = EnderecoTestMock.buildEndereco(enderecoId);

            // Mock do jdbcTemplate para simular a consulta bem-sucedida
            when(jdbcTemplate.queryForObject(any(String.class), any(Object[].class), any(RowMapper.class)))
                    .thenReturn(enderecoEsperado);

            // Executa o método
            Optional<Endereco> optionalEndereco = adapter.getEnderecoById(enderecoId);

            // Verifica o resultado
            assertTrue(optionalEndereco.isPresent());
            assertEquals(enderecoEsperado, optionalEndereco.get());

            // Verifica se a consulta SQL foi executada corretamente
            verify(jdbcTemplate).queryForObject(
                    eq("SELECT * FROM enderecos WHERE id = ?"),
                    argThat(a -> a[0].equals(enderecoId)),
                    isA(RowMapper.class));
        }

        @Test
        public void testGetEnderecoById_EnderecoNaoEncontrado() throws DataAccessException {
            UUID enderecoId = UUID.randomUUID();

            // Mock do jdbcTemplate para simular a consulta sem resultados
            when(jdbcTemplate.queryForObject(any(String.class), any(Object[].class), any(RowMapper.class))).thenReturn(null);

            // Executa o método
            Optional<Endereco> optionalEndereco = adapter.getEnderecoById(enderecoId);

            // Verifica o resultado
            assertFalse(optionalEndereco.isPresent());

            // Verifica se a consulta SQL foi executada corretamente
            verify(jdbcTemplate).queryForObject(
                    eq("SELECT * FROM enderecos WHERE id = ?"),
                    argThat(a -> a[0].equals(enderecoId)),
                    isA(RowMapper.class));
        }

        @Test
        public void testGetEnderecoById_DataAccessException() throws DataAccessException {
            UUID enderecoId = UUID.randomUUID();

            // Mock do jdbcTemplate para simular uma exceção
            when(jdbcTemplate.queryForObject(any(String.class), any(Object[].class), any(RowMapper.class)))
                    .thenThrow(new DataAccessException("Erro na consulta") {
                    });

            // Executa o método
            Optional<Endereco> optionalEndereco = adapter.getEnderecoById(enderecoId);

            // Verifica o resultado
            assertFalse(optionalEndereco.isPresent());

            // Verifica se a consulta SQL foi executada corretamente
            verify(jdbcTemplate).queryForObject(
                    eq("SELECT * FROM enderecos WHERE id = ?"),
                    argThat(a -> a[0].equals(enderecoId)),
                    isA(RowMapper.class));
        }
    }

    @Nested
    class AlterarEnderecos {
        @Test
        public void testUpdateEndereco_Sucesso() {
            UUID enderecoId = UUID.randomUUID();
            var endereco = EnderecoTestMock.buildEndereco(enderecoId);

            when(jdbcTemplate.update(anyString(), any(), any(), any(), any(), any(), any(), any()))
                    .thenReturn(1); // Simula 1 linha afetada

            assertDoesNotThrow(() -> adapter.updateEndereco(endereco));
            verify(jdbcTemplate).update(anyString(), any(), any(), any(), any(), any(), any(), any());
        }

        @Test
        public void testUpdateEndereco_Falha_NenhumaLinhaAfetada() {
            UUID enderecoId = UUID.randomUUID();
            var endereco = EnderecoTestMock.buildEndereco(enderecoId);

            when(jdbcTemplate.update(anyString(), any(), any(), any(), any(), any(), any(), any())).thenReturn(0); // Simula 0 linhas afetadas

            assertThrows(DataAccessException.class, () -> adapter.updateEndereco(endereco));
            verify(jdbcTemplate).update(anyString(), any(), any(), any(), any(), any(), any(), any());
        }

        @Test
        public void testUpdateEndereco_ExcecaoNoJdbcTemplate() {
            UUID enderecoId = UUID.randomUUID();
            var endereco = EnderecoTestMock.buildEndereco(enderecoId);

            doThrow(new DataAccessException("Erro na atualização") {
            })
                    .when(jdbcTemplate).update(anyString(), any(), any(), any(), any(), any(), any(), any());

            assertThrows(DataAccessException.class, () -> adapter.updateEndereco(endereco));
        }
    }

    @Nested
    class AlterarRestaurante {

        @Test
        public void testUpdate_Sucesso() {
            Restaurante restaurante = RestauranteTestMock.buildRestaurante(UUID.randomUUID());

            when(jdbcTemplate.update(anyString(), any(), any(), any(), any())).thenReturn(1); // Simula 1 linha afetada

            assertDoesNotThrow(() -> adapter.update(restaurante));
            verify(jdbcTemplate).update(anyString(), any(), any(), any(), any());
        }

        @Test
        public void testUpdate_ExcecaoNoJdbcTemplate() {
            Restaurante restaurante = RestauranteTestMock.buildRestaurante(UUID.randomUUID());

            doThrow(new DataAccessException("Erro na atualização") {
            })
                    .when(jdbcTemplate).update(anyString(), any(), any(), any(), any());

            assertThrows(DataAccessException.class, () -> adapter.update(restaurante));
        }
    }

    @Nested
    class DeletarRestaurante {
        @Test
        public void testDelete_Sucesso() {
            UUID restauranteId = UUID.randomUUID();

            when(jdbcTemplate.update(anyString(), Optional.ofNullable(any()))).thenReturn(1); // Simula 1 linha afetada

            assertDoesNotThrow(() -> adapter.delete(restauranteId));
            verify(jdbcTemplate).update(anyString(), Optional.ofNullable(any()));
        }

        @Test
        public void testDelete_ExcecaoNoJdbcTemplate() {
            UUID restauranteId = UUID.randomUUID();

            doThrow(new DataAccessException("Erro na atualização") {
            })
                    .when(jdbcTemplate).update(anyString(), Optional.ofNullable(any()));

            assertThrows(DataAccessException.class, () -> adapter.delete(restauranteId));
        }
    }

    @Nested
    class savarRestaurante {

        @Test
        public void testSave_Sucesso() throws SQLException {
            Restaurante restaurante = RestauranteTestMock.buildRestaurante(UUID.randomUUID());

            // Simular inserção de endereço
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
            keyHolder.getKeyList().add(Map.of("id", UUID.randomUUID()));
            when(jdbcTemplate.update(any(PreparedStatementCreator.class), any(GeneratedKeyHolder.class))).thenAnswer(invocation -> {
                ((GeneratedKeyHolder) invocation.getArgument(1)).getKeyList().add(Map.of("id", UUID.randomUUID()));
                return 1;
            });

            // Simular inserção do restaurante
            when(jdbcTemplate.update(anyString(), any(), any(), any(), any(), any())).thenReturn(1);

            // Chamar o método
            assertDoesNotThrow(() -> adapter.save(restaurante));

            // Verificar interações
            verify(jdbcTemplate, times(1)).update(any(PreparedStatementCreator.class), any(GeneratedKeyHolder.class));
            verify(jdbcTemplate, times(1)).update(anyString(), any(), any(), any(), any(), any());

        }

        @Test
        public void testSaveAddressInsertFailure() {
            Restaurante restaurante = RestauranteTestMock.buildRestaurante(UUID.randomUUID());

            // Simular falha na inserção do endereço
            when(jdbcTemplate.update(any(PreparedStatementCreator.class), any(GeneratedKeyHolder.class)))
                    .thenThrow(new DataAccessException("Falha ao inserir o endereço.") {});

            // Verificar se uma exceção é lançada
            Exception exception = assertThrows(DataAccessException.class, () -> adapter.save(restaurante));
            assertEquals("Falha ao inserir o endereço.", exception.getMessage());
        }

        @Test
        public void testSaveRestaurantInsertFailure() {
            Restaurante restaurante = RestauranteTestMock.buildRestaurante(UUID.randomUUID());

            // Simular inserção de endereço com sucesso
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
            keyHolder.getKeyList().add(Map.of("id", UUID.randomUUID()));
            when(jdbcTemplate.update(any(PreparedStatementCreator.class), any(GeneratedKeyHolder.class))).thenReturn(1);

            // Simular falha na inserção do restaurante
            when(jdbcTemplate.update(anyString(), any(), any(), any(), any(), any()))
                    .thenThrow(new DataAccessException("Database error") {});

            // Verificar se uma exceção é lançada
            Exception exception = assertThrows(DataAccessException.class, () -> adapter.save(restaurante));
            assertEquals("Falha ao inserir o endereço.", exception.getMessage());
        }

    }
}