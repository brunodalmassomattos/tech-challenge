package br.com.fiap.level3.domain.avaliacao.core;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.level3.domain.avaliacao.core.domain.model.avaliacao.Avaliacao;
import br.com.fiap.level3.domain.avaliacao.core.domain.model.exception.AddAvaliacaoException;
import br.com.fiap.level3.domain.avaliacao.core.ports.outcoming.AvaliacaoDatabase;
import br.com.fiap.level3.domain.avaliacao.mocks.AvaliacaoTestMock;
import br.com.fiap.level3.domain.avaliacao.mocks.RestauranteTestMock;
import br.com.fiap.level3.domain.avaliacao.mocks.UsuarioTestMock;
import br.com.fiap.level3.domain.exception.ControllerNotFoundException;

public class AvaliacaoFacadeTest {

	private AvaliacaoFacade avaliacaoFacade;

	@Mock
	private AvaliacaoDatabase avaliacaoDatabase;

	private AutoCloseable openMocks;

	@BeforeEach
	void setUp() {
		openMocks = MockitoAnnotations.openMocks(this);
		avaliacaoFacade = new AvaliacaoFacade(avaliacaoDatabase);
	}

	@AfterEach
	void tearDown() throws Exception {
		openMocks.close();
	}

	@Nested
	class CriaAvaliaca {

		@Test
		void criaNovaAvaliacao() {

			Avaliacao avaliacao = AvaliacaoTestMock.getAvaliacao();

			when(avaliacaoDatabase.getRestauranteById(any(UUID.class)))
					.thenReturn(Optional.of(RestauranteTestMock.buildRestaurante(null)));
			when(avaliacaoDatabase.getUsuarioById(any(UUID.class)))
					.thenReturn(Optional.of(UsuarioTestMock.getUsuario()));

			avaliacaoFacade.save(avaliacao);

			verify(avaliacaoDatabase).save(avaliacao);

		}

		@Test
		void tentaCriarAvaliacaoNotaMaiorQue10Excecao() {

			Avaliacao avaliacao = AvaliacaoTestMock.getAvaliacaoNotaMaiorQue10();

			when(avaliacaoDatabase.getRestauranteById(any(UUID.class)))
					.thenReturn(Optional.of(RestauranteTestMock.buildRestaurante(null)));
			when(avaliacaoDatabase.getUsuarioById(any(UUID.class)))
					.thenReturn(Optional.of(UsuarioTestMock.getUsuario()));

			assertThatThrownBy(() -> avaliacaoFacade.save(avaliacao)).isInstanceOf(AddAvaliacaoException.class)
					.hasMessage("Nota apenas de 0 a 10!");

			verify(avaliacaoDatabase, never()).save(any(Avaliacao.class));

		}

		@Test
		void tentaCriarAvaliacaoNotaMenorQue0Excecao() {

			Avaliacao avaliacao = AvaliacaoTestMock.getAvaliacaoNotaMenorQue0();

			when(avaliacaoDatabase.getRestauranteById(any(UUID.class)))
					.thenReturn(Optional.of(RestauranteTestMock.buildRestaurante(null)));
			when(avaliacaoDatabase.getUsuarioById(any(UUID.class)))
					.thenReturn(Optional.of(UsuarioTestMock.getUsuario()));

			assertThatThrownBy(() -> avaliacaoFacade.save(avaliacao)).isInstanceOf(AddAvaliacaoException.class)
					.hasMessage("Nota apenas de 0 a 10!");

			verify(avaliacaoDatabase, never()).save(any(Avaliacao.class));

		}

		@Test
		void tentaCriarAvaliacaoCaractereUltrapassouLimiteExcecao() {

			Avaliacao avaliacao = AvaliacaoTestMock.getAvaliacaoNumeroCaracteresForaDoLimite();

			when(avaliacaoDatabase.getRestauranteById(any(UUID.class)))
					.thenReturn(Optional.of(RestauranteTestMock.buildRestaurante(null)));
			when(avaliacaoDatabase.getUsuarioById(any(UUID.class)))
					.thenReturn(Optional.of(UsuarioTestMock.getUsuario()));

			assertThatThrownBy(() -> avaliacaoFacade.save(avaliacao)).isInstanceOf(AddAvaliacaoException.class)
					.hasMessage("Comentario excedeu o limite de caracteres!");

			verify(avaliacaoDatabase, never()).save(any(Avaliacao.class));

		}

		@Test
		void tentaCriarAvaliacaoRestauranteInexistenteExcecao() {

			Avaliacao avaliacao = AvaliacaoTestMock.getAvaliacaoRestauranteInexistente();

			when(avaliacaoDatabase.getUsuarioById(any(UUID.class)))
					.thenReturn(Optional.of(UsuarioTestMock.getUsuario()));

			assertThatThrownBy(() -> avaliacaoFacade.save(avaliacao)).isInstanceOf(ControllerNotFoundException.class)
					.hasMessage("Restaurante não encontrado!");

			verify(avaliacaoDatabase, never()).save(any(Avaliacao.class));

		}

		@Test
		void tentaCriarAvaliacaoUsuarioInexistenteExcecao() {

			Avaliacao avaliacao = AvaliacaoTestMock.getAvaliacaoUsuarioInexistente();

			when(avaliacaoDatabase.getRestauranteById(any(UUID.class)))
					.thenReturn(Optional.of(RestauranteTestMock.buildRestaurante(null)));

			assertThatThrownBy(() -> avaliacaoFacade.save(avaliacao)).isInstanceOf(ControllerNotFoundException.class)
					.hasMessage("Usuario não encontrado!");

			verify(avaliacaoDatabase, never()).save(any(Avaliacao.class));

		}

	}

	@Nested
	class BuscaAvaliacao {

		@Test
		void buscaAvaliacaoByUsuarioId() {

			UUID usuarioId = UUID.randomUUID();

			Avaliacao avaliacao = AvaliacaoTestMock.getAvaliacao();
			List<Optional<Avaliacao>> avaliacaoListMock = Collections.singletonList(Optional.of(avaliacao));

			when(avaliacaoDatabase.getAvaliacaoByUsuarioId(usuarioId)).thenReturn(avaliacaoListMock);

			List<Optional<Avaliacao>> avaliacaoList = avaliacaoFacade.getAvaliacaoByUsuarioId(usuarioId);

			assertEquals(avaliacaoList, avaliacaoListMock);

			verify(avaliacaoDatabase).getAvaliacaoByUsuarioId(usuarioId);
		}

		@Test
		void tentaBuscarAvaliacaoByUsuarioIdInexistenteExcecao() {

			UUID usuarioId = UUID.randomUUID();

			assertThatThrownBy(() -> avaliacaoFacade.getAvaliacaoByUsuarioId(usuarioId))
					.isInstanceOf(ControllerNotFoundException.class)
					.hasMessage("Nenhuma avaliação encontrada para o usuario com esse id!");

		}

		@Test
		void buscaAvaliacaoByRestauranteId() {

			UUID restauranteId = UUID.randomUUID();

			Avaliacao avaliacao = AvaliacaoTestMock.getAvaliacao();
			List<Optional<Avaliacao>> avaliacaoListMock = Collections.singletonList(Optional.of(avaliacao));

			when(avaliacaoDatabase.getAvaliacaoByRestauranteId(restauranteId)).thenReturn(avaliacaoListMock);

			List<Optional<Avaliacao>> avaliacaoList = avaliacaoFacade.getAvaliacaoByRestauranteId(restauranteId);

			assertEquals(avaliacaoList, avaliacaoListMock);

			verify(avaliacaoDatabase).getAvaliacaoByRestauranteId(restauranteId);
		}
		
		@Test
		void buscaAvaliacaoById() {
			
			UUID avalicaoId = UUID.randomUUID();

			Optional<Avaliacao> avaliacaoMock = Optional.of(AvaliacaoTestMock.getAvaliacao());

			when(avaliacaoDatabase.getAvaliacaoById(avalicaoId)).thenReturn(Optional.of(AvaliacaoTestMock.getAvaliacao()));
			
			Optional<Avaliacao> avaliacao = avaliacaoFacade.getAvaliacaoById(avalicaoId);
			
			assertEquals(avaliacao.get().getId(), avaliacaoMock.get().getId());
			
			verify(avaliacaoDatabase).getAvaliacaoById(avalicaoId);
			
		}

		@Test
		void tentaBuscarAvaliacaoByRestauranteIdInexistenteExcecao() {

			UUID restauranteId = UUID.randomUUID();

			assertThatThrownBy(() -> avaliacaoFacade.getAvaliacaoByRestauranteId(restauranteId))
					.isInstanceOf(ControllerNotFoundException.class)
					.hasMessage("Nenhuma avaliação encontrada para o restaurante com esse id!");

		}
		
		@Test
		void tentaBuscarAvaliacaoByIdInexistenteExcecao() {
			
			UUID avaliacaoId = UUID.randomUUID();
			
			assertThatThrownBy(() -> avaliacaoFacade.getAvaliacaoById(avaliacaoId))
			.isInstanceOf(ControllerNotFoundException.class)
			.hasMessage("Avaliacao não encontrada!");
		}
		

	}
	
	@Nested
	class AlterarAvaliacao{
		
		@Test
		void tentaAlterarAvaliacaoNotaMaiorQue10Excecao() {
			
			Avaliacao avaliacao = AvaliacaoTestMock.getAvaliacaoNotaMaiorQue10();
			
			assertThatThrownBy(() -> avaliacaoFacade.alterAvaliacao(avaliacao))
			.isInstanceOf(AddAvaliacaoException.class)
			.hasMessage("Nota apenas de 0 a 10!");
			
		}
		
		@Test
		void tentaAlterarAvaliacaoNotaMenorQue0Excecao() {
			
			Avaliacao avaliacao = AvaliacaoTestMock.getAvaliacaoNotaMenorQue0();
			
			assertThatThrownBy(() -> avaliacaoFacade.alterAvaliacao(avaliacao))
			.isInstanceOf(AddAvaliacaoException.class)
			.hasMessage("Nota apenas de 0 a 10!");
			
		}
		
		@Test
		void tentaAlterarAvaliacaoCaractereUltrapassouLimiteExcecao() {
			
			Avaliacao avaliacao = AvaliacaoTestMock.getAvaliacaoNumeroCaracteresForaDoLimite();
			
			assertThatThrownBy(() -> avaliacaoFacade.alterAvaliacao(avaliacao))
			.isInstanceOf(AddAvaliacaoException.class)
			.hasMessage("Comentario excedeu o limite de caracteres!");
			
		}
		
	    @Test
	    void testAlterData() {
	    	
			Avaliacao avaliacao = AvaliacaoTestMock.getAvaliacao();

			avaliacao.setComentario("Medio");
			
			when(avaliacaoDatabase.getAvaliacaoById(any(UUID.class))).thenReturn(Optional.of(AvaliacaoTestMock.getAvaliacao()));
			
			avaliacaoFacade.alterAvaliacao(avaliacao);
			
	        ArgumentCaptor<Avaliacao> captor = ArgumentCaptor.forClass(Avaliacao.class);
	        
	        verify(avaliacaoDatabase, times(1)).alterAvaliacao(captor.capture());
	        
	        Avaliacao avaliacaoResult = captor.getValue();
	        
	        assertEquals(avaliacao.getId(), avaliacaoResult.getId());
	        assertEquals("Medio", avaliacaoResult.getComentario());
			
	    }
		
		@Test
		void tentaAlterarAvaliacaoIdInexistenteExcecao() {
			
			Avaliacao avaliacao = AvaliacaoTestMock.getAvaliacao();
			
			assertThatThrownBy(() -> avaliacaoFacade.alterAvaliacao(avaliacao))
			.isInstanceOf(ControllerNotFoundException.class)
			.hasMessage("Avaliacao não encontrada!");
			
			
		}
		
		
	}

}
