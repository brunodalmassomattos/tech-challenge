package br.com.fiap.mslogistica.persistence.repository;

import br.com.fiap.mslogistica.application.enumerator.SituacaoLoteEnum;
import br.com.fiap.mslogistica.application.exception.ControllerNotFoundException;
import br.com.fiap.mslogistica.domain.entity.Lote;
import br.com.fiap.mslogistica.domain.repository.LoteRepository;
import br.com.fiap.mslogistica.mocks.LoteMock;
import br.com.fiap.mslogistica.persistence.data.DataLoteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class LoteRepositoryImplTest {

    @Mock
    private DataLoteRepository loteRepository;

    private LoteRepository repository;

    private AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        repository = new LoteRepositoryImpl(loteRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveBuscarLotePorIdComSucesso() {
        when(loteRepository.findById(any(UUID.class))).thenReturn(Optional.of(LoteMock.getLote()));

        Lote lote = repository.buscarPorId(UUID.randomUUID());

        assertThat(lote).isNotNull();
        assertThat(lote.getCepEntrega()).isEqualTo("15130-023");
        assertThat(lote.getStatus()).isEqualTo(SituacaoLoteEnum.CRIADO.getValor());
        verify(loteRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    void deveLancarExcecaoQuandoNaoEncontrarLotePorId() {
        when(loteRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        UUID loteId = UUID.randomUUID();
        assertThatThrownBy(() -> repository.buscarPorId(loteId))
                .isInstanceOf(ControllerNotFoundException.class)
                .hasMessage(String.format("Não existe lote de entrega para id: %s", loteId));

        verify(loteRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    void deveBuscarLotePorCepEsituacaoComSucesso() {
        when(loteRepository.findByCep(anyString(), anyString())).thenReturn(Optional.of(LoteMock.getLote()));

        Optional<Lote> lote = repository.buscarPorCep("15130-023", SituacaoLoteEnum.CRIADO.getValor());

        assertThat(lote).isPresent();
        assertThat(lote.get().getCepEntrega()).isEqualTo("15130-023");
        assertThat(lote.get().getStatus()).isEqualTo(SituacaoLoteEnum.CRIADO.getValor());
        verify(loteRepository, times(1)).findByCep(anyString(), anyString());
    }

    @Test
    void deveRetornarLoteVazioQuandoNaoEncontrarLotePorCepEsituacao() {
        when(loteRepository.findByCep(anyString(), anyString())).thenReturn(Optional.empty());
        Optional<Lote> lote = repository.buscarPorCep("15130-023", SituacaoLoteEnum.CRIADO.getValor());
        assertThat(lote).isEmpty();
        verify(loteRepository, times(1)).findByCep(anyString(), anyString());
    }
}