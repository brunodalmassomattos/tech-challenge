package br.com.fiap.level3.domain.avaliacao.infrastructure;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test; // Alterado para JUnit 5
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import br.com.fiap.level3.domain.avaliacao.core.domain.model.avaliacao.Avaliacao;
import br.com.fiap.level3.domain.avaliacao.mock.AvaliacaoTestMock;
import br.com.fiap.level3.domain.avaliacao.mock.RestauranteTestMock;
import br.com.fiap.level3.domain.avaliacao.mock.UsuarioTestMock;
import br.com.fiap.level3.domain.reserva.core.model.usuario.Usuario;
import br.com.fiap.level3.domain.restaurante.core.model.restaurante.Restaurante;
import jakarta.persistence.EntityManager;

class AvaliacaoDatabaseAdapterTest {

	@Mock
	private JdbcTemplate jdbcTemplate;

	private AutoCloseable openMocks;

	private AvaliacaoDatabaseAdapter avaliacaoDatabaseAdapter;

	@Mock
	private EntityManager entityManager;

	@BeforeEach
	void setUp() {
		openMocks = MockitoAnnotations.openMocks(this);
		avaliacaoDatabaseAdapter = new AvaliacaoDatabaseAdapter(jdbcTemplate, entityManager);
	}

	@AfterEach
	void tearDown() throws Exception {
		openMocks.close();
	}

	@Nested
	class BuscarAvaliacao {

		@Test
		public void buscaAvaliacaoByIdEncontrada() throws DataAccessException {

			UUID usuarioId = UUID.randomUUID();
			Avaliacao avaliacao = AvaliacaoTestMock.getAvaliacao();
			avaliacao.setId(usuarioId);

			when(jdbcTemplate.queryForObject(any(String.class), any(RowMapper.class), any(Object.class)))
					.thenReturn(avaliacao);

			Optional<Avaliacao> optionalAvaliacao = avaliacaoDatabaseAdapter.getAvaliacaoById(usuarioId);

			assertTrue(optionalAvaliacao.isPresent());
		}

		@Test
		void tentaBuscarAvaliacaoIdInexistenteNaoLocalizado() {

			UUID usuarioId = UUID.randomUUID();

			when(jdbcTemplate.queryForObject(any(String.class), any(RowMapper.class), any(Object.class)))
					.thenReturn(null);

			Optional<Avaliacao> optionalAvaliacao = avaliacaoDatabaseAdapter.getAvaliacaoById(usuarioId);

			assertFalse(optionalAvaliacao.isPresent());

		}

		@Test
		void buscaAvaliacaoByRestauranteIdEncontrada() {

			UUID restauranteId = UUID.randomUUID();

			Avaliacao avaliacaoMock = AvaliacaoTestMock.getAvaliacao();

			when(jdbcTemplate.query(any(String.class), any(RowMapper.class), any(Object.class)))
					.thenReturn(Collections.singletonList(avaliacaoMock));

			List<Optional<Avaliacao>> optionalAvaliacao = avaliacaoDatabaseAdapter
					.getAvaliacaoByRestauranteId(restauranteId);

			assertFalse(optionalAvaliacao.isEmpty());
			assertTrue(optionalAvaliacao.get(0).isPresent());
			assertEquals(avaliacaoMock, optionalAvaliacao.get(0).get());

		}

		@Test
		void tentaBuscarAvaliacaoByRestauranteIdNaoLocalizado() {

			UUID restauranteId = UUID.randomUUID();

			Avaliacao avaliacaoMock = AvaliacaoTestMock.getAvaliacao();

			when(jdbcTemplate.query(any(String.class), any(RowMapper.class), any(Object.class)))
					.thenReturn(Collections.singletonList(null));

			List<Optional<Avaliacao>> optionalAvaliacao = avaliacaoDatabaseAdapter
					.getAvaliacaoByRestauranteId(restauranteId);

			assertFalse(optionalAvaliacao.get(0).isPresent());

		}

		@Test
		void buscaAvaliacaoByUsuarioIdEncontrada() {

			UUID usuarioId = UUID.randomUUID();

			Avaliacao avaliacaoMock = AvaliacaoTestMock.getAvaliacao();

			when(jdbcTemplate.query(any(String.class), any(RowMapper.class), any(Object.class)))
					.thenReturn(Collections.singletonList(avaliacaoMock));

			List<Optional<Avaliacao>> optionalAvaliacao = avaliacaoDatabaseAdapter
					.getAvaliacaoByRestauranteId(usuarioId);

			assertFalse(optionalAvaliacao.isEmpty());
			assertTrue(optionalAvaliacao.get(0).isPresent());
			assertEquals(avaliacaoMock, optionalAvaliacao.get(0).get());

		}

		@Test
		void tentaBuscarAvaliacaoByUsuarioIdNaoLocalizado() {

			UUID usuarioId = UUID.randomUUID();

			Avaliacao avaliacaoMock = AvaliacaoTestMock.getAvaliacao();

			when(jdbcTemplate.query(any(String.class), any(RowMapper.class), any(Object.class)))
					.thenReturn(Collections.singletonList(null));

			List<Optional<Avaliacao>> optionalAvaliacao = avaliacaoDatabaseAdapter.getAvaliacaoByUsuarioId(usuarioId);

			assertFalse(optionalAvaliacao.get(0).isPresent());

		}

	}

	@Nested
	class AlterarAvaliacao {

		@Test
		void alterarAvaliacaoSucesso() {

			Avaliacao avaliacaoMock = AvaliacaoTestMock.getAvaliacao();

			when(jdbcTemplate.update(anyString(), any(), any(), any())).thenReturn(1);

			assertDoesNotThrow(() -> avaliacaoDatabaseAdapter.alterAvaliacao(avaliacaoMock));
			verify(jdbcTemplate).update(anyString(), any(), any(), any());

		}

	}

	@Nested
	class SalvarAvaliacao {

		@Test
		void salvarAvaliacao() {

			Avaliacao avaliacaoMock = AvaliacaoTestMock.getAvaliacao();

			when(jdbcTemplate.update(anyString(), any(), any(), any(), any())).thenReturn(1);

			assertDoesNotThrow(() -> avaliacaoDatabaseAdapter.save(avaliacaoMock));

			verify(jdbcTemplate, times(1)).update(anyString(), any(), any(), any(), any());

		}

	}
	
	@Nested
	class BuscaRestaurante {
		
		@Test
		void buscarRestauranteByIdEncontrada() {
			
			UUID restauranteId = UUID.randomUUID();
			Restaurante restaurante = RestauranteTestMock.buildRestaurante(null);
			restaurante.setId(restauranteId);

			when(jdbcTemplate.queryForObject(any(String.class), any(RowMapper.class), any(Object.class)))
					.thenReturn(restaurante);

			Optional<Restaurante> optionalAvaliacao = avaliacaoDatabaseAdapter.getRestauranteById(restauranteId);

			assertTrue(optionalAvaliacao.isPresent());
			assertEquals(restaurante, optionalAvaliacao.get());
			
		}
		
		@Test
		void tentaBuscarRestauranteByIdNaoLocalizado() {
			
			UUID restauranteId = UUID.randomUUID();

			when(jdbcTemplate.queryForObject(any(String.class), any(RowMapper.class), any(Object.class)))
					.thenReturn(null);

			Optional<Restaurante> optionalAvaliacao = avaliacaoDatabaseAdapter.getRestauranteById(restauranteId);

			assertFalse(optionalAvaliacao.isPresent());
			
		}
		
	}
	
	@Nested
	class BuscarUsuario{
		
		@Test
		void buscarUsuarioByIdEncontrado() {
		    
			UUID usuarioId = UUID.randomUUID();
		    Usuario usuario = UsuarioTestMock.getUsuario();
		    usuario.setId(usuarioId);
		    
		    when(entityManager.find(Usuario.class, usuarioId)).thenReturn(usuario);

		    Optional<Usuario> optionalUsuario = avaliacaoDatabaseAdapter.getUsuarioById(usuarioId);
		    
		    assertTrue(optionalUsuario.isPresent());
		    assertEquals(usuario, optionalUsuario.get());
		}
		
		
		@Test
		void tentaBuscarUsuarioByIdEncontradoNaoLocalizado() {
			
			UUID usuarioId = UUID.randomUUID();
		    Usuario usuario = UsuarioTestMock.getUsuario();
		    usuario.setId(usuarioId);
		    
		    when(entityManager.find(Usuario.class, usuarioId)).thenReturn(null);
		
			Optional<Usuario> optionalAvaliacao = avaliacaoDatabaseAdapter.getUsuarioById(usuarioId);

			assertFalse(optionalAvaliacao.isPresent());
		    
		}
	}
	

	

}
