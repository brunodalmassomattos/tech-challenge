package br.com.fiap.level3.domain.avaliacao.application;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.fiap.level3.domain.avaliacao.core.domain.model.avaliacao.AlterarAvaliacaoDTO;
import br.com.fiap.level3.domain.avaliacao.core.domain.model.avaliacao.Avaliacao;
import br.com.fiap.level3.domain.avaliacao.core.domain.model.avaliacao.AvaliacaoDTO;
import br.com.fiap.level3.domain.avaliacao.core.ports.incoming.AddAvaliacao;
import br.com.fiap.level3.domain.avaliacao.core.ports.incoming.AlterAvaliacao;
import br.com.fiap.level3.domain.avaliacao.core.ports.incoming.FindAvaliacao;
import br.com.fiap.level3.domain.avaliacao.mock.AvaliacaoDTOTestMock;
import br.com.fiap.level3.domain.avaliacao.mock.AvaliacaoTestMock;

public class AvaliacaoControllerTest {

	@Mock
	private FindAvaliacao findAvaliacao;

	@Mock
	private AddAvaliacao addAvaliacao;

	@Mock
	private AlterAvaliacao alterAvaliacao;

	private AvaliacaoController avaliacaoController;

	private AutoCloseable openMocks;

	@BeforeEach
	public void setUp() {
		openMocks = MockitoAnnotations.openMocks(this);
		avaliacaoController = new AvaliacaoController(findAvaliacao, addAvaliacao, alterAvaliacao);
	}

	@AfterEach
	void tearDown() throws Exception {
		openMocks.close();
	}

	@Nested
	class CadastraAvaliacao {

		@Test
		void cadastrarNovaAvaliacaoSucesso() {

			AvaliacaoDTO avaliacaoDTO = AvaliacaoDTOTestMock.getAvaliacaoDTO();

			ResponseEntity<String> response = avaliacaoController.adicionarAvaliacao(avaliacaoDTO);

			assertEquals(HttpStatus.CREATED, response.getStatusCode());

			verify(addAvaliacao).save(any(Avaliacao.class));

		}

	}

	@Nested
	class BuscaAvaliacao {

		@Test
		void buscaAvaliacaoByIdSucesso() {

			Avaliacao avaliacao = AvaliacaoTestMock.getAvaliacao();

			UUID avaliacaoId = UUID.randomUUID();

			when(findAvaliacao.getAvaliacaoById(avaliacaoId)).thenReturn(Optional.of(avaliacao));

			ResponseEntity<AvaliacaoDTO> response = avaliacaoController.buscarAvaliacaoPorId(avaliacaoId.toString());

			assertEquals(HttpStatus.OK, response.getStatusCode());

			assertEquals(response.getBody().id(), avaliacao.getId().toString());

		}

		@Test
		void tentaBuscarAvaliacaoByIdNaoLocalizado() {

			UUID avaliacaoId = UUID.randomUUID();

			when(findAvaliacao.getAvaliacaoById(avaliacaoId)).thenReturn(Optional.empty());

			ResponseEntity<AvaliacaoDTO> response = avaliacaoController.buscarAvaliacaoPorId(avaliacaoId.toString());

			assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

		}

		@Test
		void buscaAvaliacaoByRestauranteIdSucesso() {

			UUID restauranteId = UUID.randomUUID();

			List<Optional<Avaliacao>> avaliacaoListMock = Collections
					.singletonList(Optional.of(AvaliacaoTestMock.getAvaliacao()));

			when(findAvaliacao.getAvaliacaoByRestauranteId(restauranteId)).thenReturn(avaliacaoListMock);

			ResponseEntity<List<AvaliacaoDTO>> response = avaliacaoController
					.buscarAvaliacaoPorRestauranteId(restauranteId.toString());

			assertEquals(HttpStatus.OK, response.getStatusCode());

			assertFalse(response.getBody().isEmpty());

		}

		@Test
		void tentaBuscarAvaliacaoByRestauranteIdNaoLocalizado() {

			UUID restauranteId = UUID.randomUUID();

			when(findAvaliacao.getAvaliacaoByRestauranteId(restauranteId)).thenReturn(Collections.emptyList());

			ResponseEntity<List<AvaliacaoDTO>> response = avaliacaoController
					.buscarAvaliacaoPorRestauranteId(restauranteId.toString());

			assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

			
		}
		
		@Test
		void buscaAvaliacaoByUsuarioIdSucesso() {

			UUID usuarioId = UUID.randomUUID();

			List<Optional<Avaliacao>> avaliacaoListMock = Collections
					.singletonList(Optional.of(AvaliacaoTestMock.getAvaliacao()));

			when(findAvaliacao.getAvaliacaoByUsuarioId(usuarioId)).thenReturn(avaliacaoListMock);

			ResponseEntity<List<AvaliacaoDTO>> response = avaliacaoController
					.buscarAvaliacaoPorUsuarioId(usuarioId.toString());

			assertEquals(HttpStatus.OK, response.getStatusCode());

			assertFalse(response.getBody().isEmpty());

		}

		@Test
		void tentaBuscarAvaliacaoByUsuarioIdNaoLocalizado() {

			UUID usuarioId = UUID.randomUUID();

			when(findAvaliacao.getAvaliacaoByUsuarioId(usuarioId)).thenReturn(Collections.emptyList());

			ResponseEntity<List<AvaliacaoDTO>> response = avaliacaoController
					.buscarAvaliacaoPorUsuarioId(usuarioId.toString());

			assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

			
		}

	}
	
	@Nested
	class AlterarAvaliacao{
		
		@Test
		void alterarAvaliacaoComentarioSucesso() {

			UUID usuarioId = UUID.randomUUID();
		    AlterarAvaliacaoDTO alterarAvaliacaoDTO = AlterarAvaliacaoDTO.builder()
		                                            .comentario("Sei La")
		                                            .build();

		    ResponseEntity<String> response = avaliacaoController.alteraAvaliacao(usuarioId.toString(), alterarAvaliacaoDTO);
		    
		    assertEquals(HttpStatus.OK, response.getStatusCode());
		    
		    ArgumentCaptor<Avaliacao> avaliacaoCaptor = ArgumentCaptor.forClass(Avaliacao.class);
		    verify(alterAvaliacao).alterAvaliacao(avaliacaoCaptor.capture());
		    
		    Avaliacao capturedAvaliacao = avaliacaoCaptor.getValue();
		    assertEquals(usuarioId, capturedAvaliacao.getId());
		    assertEquals(alterarAvaliacaoDTO.comentario(), capturedAvaliacao.getComentario());
		    
		}
		
		@Test
		void alterarAvaliacaoComentarioENotaSucesso() {

			UUID usuarioId = UUID.randomUUID();
		    AlterarAvaliacaoDTO alterarAvaliacaoDTO = AlterarAvaliacaoDTO.builder()
		                                            .nota(10)
		                                            .comentario("Sei La")
		                                            .build();

		    ResponseEntity<String> response = avaliacaoController.alteraAvaliacao(usuarioId.toString(), alterarAvaliacaoDTO);
		    
		    assertEquals(HttpStatus.OK, response.getStatusCode());
		    
		    ArgumentCaptor<Avaliacao> avaliacaoCaptor = ArgumentCaptor.forClass(Avaliacao.class);
		    verify(alterAvaliacao).alterAvaliacao(avaliacaoCaptor.capture());
		    
		    Avaliacao capturedAvaliacao = avaliacaoCaptor.getValue();
		    assertEquals(usuarioId, capturedAvaliacao.getId());
		    assertEquals(alterarAvaliacaoDTO.nota(), capturedAvaliacao.getNota());
		    assertEquals(alterarAvaliacaoDTO.comentario(), capturedAvaliacao.getComentario());
		    
		}
		
		@Test
		void alterarAvaliacaoNotaSucesso() {

			UUID usuarioId = UUID.randomUUID();
		    AlterarAvaliacaoDTO alterarAvaliacaoDTO = AlterarAvaliacaoDTO.builder()
		                                            .nota(10)
		                                            .build();

		    ResponseEntity<String> response = avaliacaoController.alteraAvaliacao(usuarioId.toString(), alterarAvaliacaoDTO);
		    
		    assertEquals(HttpStatus.OK, response.getStatusCode());
		    
		    ArgumentCaptor<Avaliacao> avaliacaoCaptor = ArgumentCaptor.forClass(Avaliacao.class);
		    verify(alterAvaliacao).alterAvaliacao(avaliacaoCaptor.capture());
		    
		    Avaliacao capturedAvaliacao = avaliacaoCaptor.getValue();
		    assertEquals(usuarioId, capturedAvaliacao.getId());
		    assertEquals(alterarAvaliacaoDTO.nota(), capturedAvaliacao.getNota());
		    
		}
		
		
	}

}
