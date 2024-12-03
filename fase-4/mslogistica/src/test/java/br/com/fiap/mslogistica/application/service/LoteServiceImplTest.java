package br.com.fiap.mslogistica.application.service;

import br.com.fiap.mslogistica.application.dto.LocalizacaoDto;
import br.com.fiap.mslogistica.application.dto.LoteEntregaResponseDto;
import br.com.fiap.mslogistica.application.enumerator.SituacaoLoteEnum;
import br.com.fiap.mslogistica.application.exception.ControllerNotFoundException;
import br.com.fiap.mslogistica.domain.entity.Lote;
import br.com.fiap.mslogistica.domain.repository.LoteRepository;
import br.com.fiap.mslogistica.domain.service.LoteService;
import br.com.fiap.mslogistica.mocks.LocalizacaoDtoMock;
import br.com.fiap.mslogistica.mocks.LoteMock;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class LoteServiceImplTest {

    private LoteService service;

    @Mock
    private LoteRepository repository;

    private AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        service = new LoteServiceImpl(repository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveBuscarLotePorCep() {
        Lote lote = LoteMock.getLote();

        when(repository.buscarPorCep(anyString(), anyString())).thenReturn(Optional.of(lote));
        Optional<Lote> loteRetornado = service.buscarLotePorCep("15130-023");

        assertThat(loteRetornado.isPresent()).isTrue();
        assertThat(loteRetornado.get()).isEqualTo(lote);
        assertThat(loteRetornado.get().getEntregas()).size().isGreaterThan(0);
        verify(repository).buscarPorCep(anyString(), anyString());
    }


    @Test
    void deveRetornarVazioAoBuscarLotePorCep() {
        when(repository.buscarPorCep(anyString(), anyString())).thenReturn(Optional.empty());
        Optional<Lote> loteRetornado = service.buscarLotePorCep("15130-023");

        assertThat(loteRetornado.isEmpty()).isTrue();
        assertThat(loteRetornado).isEmpty();
        verify(repository).buscarPorCep(anyString(), anyString());
    }

    @Test
    void deveAtualizarLocalizacaoComSucesso() {
        Lote lote = LoteMock.getLote();
        LocalizacaoDto localizacaoDto = LocalizacaoDtoMock.getLocalizacaoDto();

        when(repository.buscarPorId(any(UUID.class))).thenReturn(lote);
        when(repository.persistir(any(Lote.class))).thenReturn(lote);
        LoteEntregaResponseDto dtoRetorno = service.atualizarLocalizacao(UUID.randomUUID(), localizacaoDto);

        assertThat(dtoRetorno).isNotNull();
        assertThat(dtoRetorno.localizacaoDto()).isNotNull();
        assertThat(dtoRetorno.localizacaoDto().latitude()).isEqualTo(localizacaoDto.latitude());
        assertThat(dtoRetorno.localizacaoDto().longitude()).isEqualTo(localizacaoDto.longitude());
        assertThat(dtoRetorno.entregasDtos().get(0).localizacaoDto().latitude()).isEqualTo(localizacaoDto.latitude());
        assertThat(dtoRetorno.entregasDtos().get(0).localizacaoDto().longitude()).isEqualTo(localizacaoDto.longitude());

        verify(repository, times(1)).persistir(any(Lote.class));
        verify(repository, times(1)).buscarPorId(any(UUID.class));
    }

    @Test
    void deveAtribuirTransportadoraComSucesso() {
        Lote lote = LoteMock.getLote();
        String transportadora = "JadLog";

        when(repository.buscarPorId(any(UUID.class))).thenReturn(lote);
        when(repository.persistir(any(Lote.class))).thenReturn(lote);
        LoteEntregaResponseDto dtoRetorno = service.atribuirTransportadora(UUID.randomUUID(), transportadora);

        assertThat(dtoRetorno).isNotNull();
        assertThat(dtoRetorno.trasportadora()).isNotNull().isEqualTo(transportadora);
        assertThat(dtoRetorno.situacao().ordinal()).isLessThanOrEqualTo(2);

        verify(repository, times(1)).persistir(any(Lote.class));
        verify(repository, times(1)).buscarPorId(any(UUID.class));
    }

    @Test
    void deveLancarExcecaoAoAtribuirTransportadoraDepoisDespachado() {
        Lote lote = LoteMock.getLoteDespachado();
        String transportadora = "JadLog";

        when(repository.buscarPorId(any(UUID.class))).thenReturn(lote);

        assertThatThrownBy(() -> service.atribuirTransportadora(UUID.randomUUID(), transportadora))
                .isInstanceOf(ControllerNotFoundException.class)
                .hasMessage(String.format("Não é permitido alterar transportadora com lote nas situações: %s ou %s",
                        SituacaoLoteEnum.ENTRADA_FILIAL.getValor(), SituacaoLoteEnum.DESPACHADO.getValor()));

        verify(repository, times(0)).persistir(any(Lote.class));
        verify(repository, times(1)).buscarPorId(any(UUID.class));
    }

    @Test
    void deveLancarExcecaoAoAtribuirTransportadoraQuandoJaTiverTransportador() {
        Lote lote = LoteMock.getLoteComTrasportadora();
        String transportadora = "JadLog";

        when(repository.buscarPorId(any(UUID.class))).thenReturn(lote);

        assertThatThrownBy(() -> service.atribuirTransportadora(UUID.randomUUID(), transportadora))
                .isInstanceOf(ControllerNotFoundException.class)
                .hasMessage(String.format("Já existe transportadora atribuída ao lote: %s", lote.getId()));

        verify(repository, times(0)).persistir(any(Lote.class));
        verify(repository, times(1)).buscarPorId(any(UUID.class));
    }

    @Test
    void deveAtualizarSituacaoComSucesso() {
        Lote lote = LoteMock.getLote();

        when(repository.buscarPorId(any(UUID.class))).thenReturn(lote);
        when(repository.persistir(any(Lote.class))).thenReturn(lote);
        LoteEntregaResponseDto dtoRetorno = service.atualizarSituacao(UUID.randomUUID(), SituacaoLoteEnum.EM_SEPARACAO.getValor());

        assertThat(dtoRetorno).isNotNull();
        assertThat(dtoRetorno.situacao()).isNotNull().isEqualTo(SituacaoLoteEnum.EM_SEPARACAO);
        assertThat(dtoRetorno.entregasDtos().get(0).situacao().getValor()).isEqualTo(SituacaoLoteEnum.EM_SEPARACAO.getValor());

        verify(repository, times(1)).persistir(any(Lote.class));
        verify(repository, times(1)).buscarPorId(any(UUID.class));
    }

    @Test
    void deveAtualizarSituacaoComSucessoAoAtualizarSituacaoParaProblema() {
        Lote lote = LoteMock.getLoteDespachado();

        when(repository.buscarPorId(any(UUID.class))).thenReturn(lote);
        when(repository.persistir(any(Lote.class))).thenReturn(lote);
        LoteEntregaResponseDto dtoRetorno = service.atualizarSituacao(UUID.randomUUID(), SituacaoLoteEnum.PROBLEMA.getValor());

        assertThat(dtoRetorno).isNotNull();
        assertThat(dtoRetorno.situacao()).isNotNull().isEqualTo(SituacaoLoteEnum.PROBLEMA);
        assertThat(dtoRetorno.entregasDtos().get(0).situacao().getValor()).isEqualTo(SituacaoLoteEnum.PROBLEMA.getValor());

        verify(repository, times(1)).persistir(any(Lote.class));
        verify(repository, times(1)).buscarPorId(any(UUID.class));
    }

    @Test
    void deveAtualizarParaQualquerSituacaoComSucessoQuandoLoteEstiverComSituacaoProblema() {
        Lote lote = LoteMock.getLoteComProblema();

        when(repository.buscarPorId(any(UUID.class))).thenReturn(lote);
        when(repository.persistir(any(Lote.class))).thenReturn(lote);
        LoteEntregaResponseDto dtoRetorno = service.atualizarSituacao(UUID.randomUUID(), SituacaoLoteEnum.DESPACHADO.getValor());

        assertThat(dtoRetorno).isNotNull();
        assertThat(dtoRetorno.situacao()).isNotNull().isEqualTo(SituacaoLoteEnum.DESPACHADO);
        assertThat(dtoRetorno.entregasDtos().get(0).situacao().getValor()).isEqualTo(SituacaoLoteEnum.DESPACHADO.getValor());

        verify(repository, times(1)).persistir(any(Lote.class));
        verify(repository, times(1)).buscarPorId(any(UUID.class));
    }

    @Test
    void deveLancarExcecaoAoAtualizarSituacaoQueEntrouNafilial() {
        Lote lote = LoteMock.getLoteChegadaEmFilial();

        when(repository.buscarPorId(any(UUID.class))).thenReturn(lote);

        assertThatThrownBy(() -> service.atualizarSituacao(UUID.randomUUID(), SituacaoLoteEnum.EM_SEPARACAO.getValor()))
                .isInstanceOf(ControllerNotFoundException.class)
                .hasMessage(String.format("Não é permitido alterar situação, quando lote se encontra na situação: %s", lote.getStatus()));

        verify(repository, times(0)).persistir(any(Lote.class));
        verify(repository, times(1)).buscarPorId(any(UUID.class));
    }

    @Test
    void deveLancarExcecaoAoAtualizarSituacaoQuandoSituacaoNaoForProximaEtapa() {
        Lote lote = LoteMock.getLoteComTrasportadora();

        when(repository.buscarPorId(any(UUID.class))).thenReturn(lote);

        assertThatThrownBy(() -> service.atualizarSituacao(UUID.randomUUID(), SituacaoLoteEnum.ENTRADA_FILIAL.getValor()))
                .isInstanceOf(ControllerNotFoundException.class)
                .hasMessage(String.format("A situação: %s não corresponde a próxima etapa", SituacaoLoteEnum.ENTRADA_FILIAL.getValor()));

        verify(repository, times(0)).persistir(any(Lote.class));
        verify(repository, times(1)).buscarPorId(any(UUID.class));
    }

    @Test
    void deveLancarExcecaoAoAtualizarSituacaoParaDespachadoSemTransportadoraAtribuida() {
        Lote lote = LoteMock.getLoteSemTransportadora();

        when(repository.buscarPorId(any(UUID.class))).thenReturn(lote);

        assertThatThrownBy(() -> service.atualizarSituacao(UUID.randomUUID(), SituacaoLoteEnum.DESPACHADO.getValor()))
                .isInstanceOf(ControllerNotFoundException.class)
                .hasMessage(String.format("Não é permitido avançar situação para %s sem transportadora atribuída", SituacaoLoteEnum.DESPACHADO.getValor()));

        verify(repository, times(0)).persistir(any(Lote.class));
        verify(repository, times(1)).buscarPorId(any(UUID.class));
    }

    @Test
    void deveBuscarEntregasComSucesso() {
        when(repository.buscarPorId(any(UUID.class))).thenReturn(LoteMock.getLoteComTrasportadora());
        LoteEntregaResponseDto dtoRetorno = service.buscarEntregas(UUID.randomUUID());
        assertThat(dtoRetorno).isNotNull();
        assertThat(dtoRetorno.entregasDtos().size()).isGreaterThan(0);
        verify(repository, times(1)).buscarPorId(any(UUID.class));
    }

    @Test
    void deveLancarExcecaoSeLoteNaoTerEntregas() {
        Lote lote = LoteMock.getLoteSemEntregas();
        when(repository.buscarPorId(any(UUID.class))).thenReturn(lote);

        assertThatThrownBy(() -> service.buscarEntregas(UUID.randomUUID()))
                .isInstanceOf(ControllerNotFoundException.class)
                .hasMessage(String.format("Não existe entregas para este lote: %s", lote.getId().toString()));

        verify(repository, times(1)).buscarPorId(any(UUID.class));
    }

    @Test
    void deveLancarExcecaoSeEntregasForNull() {
        Lote lote = LoteMock.getLoteComEntregasNull();
        when(repository.buscarPorId(any(UUID.class))).thenReturn(lote);

        assertThatThrownBy(() -> service.buscarEntregas(UUID.randomUUID()))
                .isInstanceOf(ControllerNotFoundException.class)
                .hasMessage(String.format("Não existe entregas para este lote: %s", lote.getId().toString()));

        verify(repository, times(1)).buscarPorId(any(UUID.class));
    }
}