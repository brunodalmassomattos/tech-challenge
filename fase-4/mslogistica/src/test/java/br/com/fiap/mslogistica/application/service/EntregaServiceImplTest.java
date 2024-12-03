package br.com.fiap.mslogistica.application.service;

import br.com.fiap.mslogistica.application.dto.EntregaResponseDto;
import br.com.fiap.mslogistica.application.dto.LocalizacaoDto;
import br.com.fiap.mslogistica.application.dto.PedidoProducerDto;
import br.com.fiap.mslogistica.application.enumerator.SituacaoEnum;
import br.com.fiap.mslogistica.application.event.PedidoProducer;
import br.com.fiap.mslogistica.application.exception.ControllerNotFoundException;
import br.com.fiap.mslogistica.application.gateway.ClienteGateway;
import br.com.fiap.mslogistica.domain.entity.Entrega;
import br.com.fiap.mslogistica.domain.repository.EntregaRepository;
import br.com.fiap.mslogistica.domain.service.EnderecoService;
import br.com.fiap.mslogistica.domain.service.EntregaService;
import br.com.fiap.mslogistica.domain.service.LoteService;
import br.com.fiap.mslogistica.mocks.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class EntregaServiceImplTest {

    private EntregaService service;

    @Mock
    private EntregaRepository repository;

    @Mock
    private EnderecoService enderecoService;

    @Mock
    private LoteService loteService;

    @Mock
    private ClienteGateway gateway;

    @Mock
    private PedidoProducer producer;

    private AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        service = new EntregaServiceImpl(enderecoService, loteService, repository, gateway, producer);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveCriarEntregaComSucessoQuandoTerLoteCriado() {

        when(gateway.obterClientePorId(any(String.class))).thenReturn(ClienteDtoMock.getClienteDto());
        when(enderecoService.buscarEnderecoPorId(any(UUID.class))).thenReturn(EnderecoMock.getEndereco());
        when(loteService.buscarLotePorCep(any(String.class))).thenReturn(Optional.of(LoteMock.getLoteEncontrado()));
        when(repository.persistir(any(Entrega.class))).thenReturn(EntregaMock.getEntregaCriada());
        doNothing().when(producer).sendMessage(anyString(), any(PedidoProducerDto.class));

        service.criarEntrega(PedidoConsumerDtoMock.getPedidoConsumerDto());

        verify(gateway, times(1)).obterClientePorId(any(String.class));
        verify(producer, times(1)).sendMessage(anyString(), any(PedidoProducerDto.class));
        verify(repository, times(1)).persistir(any(Entrega.class));
        verify(enderecoService, times(1)).buscarEnderecoPorId(any(UUID.class));
        verify(loteService, times(1)).buscarLotePorCep(any(String.class));
    }

    @Test
    void deveCriarEntregaComSucessoQuandoNaoEncontrarLoteCriadoParaCep() {
        when(gateway.obterClientePorId(any(String.class))).thenReturn(ClienteDtoMock.getClienteDto());
        when(enderecoService.buscarEnderecoPorId(any(UUID.class))).thenReturn(EnderecoMock.getEndereco());
        when(loteService.buscarLotePorCep(any(String.class))).thenReturn(Optional.empty());
        when(repository.persistir(any(Entrega.class))).thenReturn(EntregaMock.getEntregaCriada());
        doNothing().when(producer).sendMessage(anyString(), any(PedidoProducerDto.class));

        service.criarEntrega(PedidoConsumerDtoMock.getPedidoConsumerDto());

        verify(gateway, times(1)).obterClientePorId(any(String.class));
        verify(producer, times(1)).sendMessage(anyString(), any(PedidoProducerDto.class));
        verify(repository, times(1)).persistir(any(Entrega.class));
        verify(enderecoService, times(1)).buscarEnderecoPorId(any(UUID.class));
        verify(loteService, times(1)).buscarLotePorCep(any(String.class));
    }

    @Test
    void deveAtualizarSituacaoComSucesso() {
        Entrega entrega = EntregaMock.getEntregaRecebidaEmFilial();
        when(repository.buscarPorId(any(UUID.class))).thenReturn(entrega);
        when(repository.persistir(any(Entrega.class))).thenReturn(entrega);
        EntregaResponseDto dtoRetorno = service.atualizarSituacao(UUID.randomUUID(), SituacaoEnum.EM_ROTA_ENTREGA.getValor());

        assertThat(dtoRetorno).isNotNull();
        assertThat(dtoRetorno.situacao()).isNotNull().isEqualTo(SituacaoEnum.EM_ROTA_ENTREGA);
        assertThat(dtoRetorno.dataHoraEntrega()).isNull();

        verify(repository, times(1)).persistir(any(Entrega.class));
        verify(repository, times(1)).buscarPorId(any(UUID.class));
    }

    @Test
    void deveAtribuirDataHoraEntregaAoAtualizarSituacaoComSucessoParaSituacaoEntregue() {
        Entrega entrega = EntregaMock.getEntregaEmRotaDeEntrega();
        when(repository.buscarPorId(any(UUID.class))).thenReturn(entrega);
        when(repository.persistir(any(Entrega.class))).thenReturn(entrega);
        EntregaResponseDto dtoRetorno = service.atualizarSituacao(UUID.randomUUID(), SituacaoEnum.ENTREGUE.getValor());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
        assertThat(dtoRetorno).isNotNull();
        assertThat(dtoRetorno.situacao()).isNotNull().isEqualTo(SituacaoEnum.ENTREGUE);
        assertThat(dtoRetorno.dataHoraEntrega()).isNotNull().isEqualTo(LocalDateTime.now().format(formatter));

        verify(repository, times(1)).persistir(any(Entrega.class));
        verify(repository, times(1)).buscarPorId(any(UUID.class));
    }

    @Test
    void deveAtualizarSituacaoComSucessoAoAtualizarSituacaoParaProblema() {
        Entrega entrega = EntregaMock.getEntregaEmRotaDeEntrega();
        when(repository.buscarPorId(any(UUID.class))).thenReturn(entrega);
        when(repository.persistir(any(Entrega.class))).thenReturn(entrega);
        EntregaResponseDto dtoRetorno = service.atualizarSituacao(UUID.randomUUID(), SituacaoEnum.PROBLEMA.getValor());

        assertThat(dtoRetorno).isNotNull();
        assertThat(dtoRetorno.situacao()).isNotNull().isEqualTo(SituacaoEnum.PROBLEMA);
        assertThat(dtoRetorno.dataHoraEntrega()).isNull();

        verify(repository, times(1)).persistir(any(Entrega.class));
        verify(repository, times(1)).buscarPorId(any(UUID.class));
    }

    @Test
    void deveAtualizarParaQualquerSituacaoComSucessoQuandoEntregaEstiverEmProblema() {
        Entrega entrega = EntregaMock.getEntregaComProblema();
        when(repository.buscarPorId(any(UUID.class))).thenReturn(entrega);
        when(repository.persistir(any(Entrega.class))).thenReturn(entrega);
        EntregaResponseDto dtoRetorno = service.atualizarSituacao(UUID.randomUUID(), SituacaoEnum.ENTRADA_FILIAL.getValor());

        assertThat(dtoRetorno).isNotNull();
        assertThat(dtoRetorno.situacao()).isNotNull().isEqualTo(SituacaoEnum.ENTRADA_FILIAL);
        assertThat(dtoRetorno.dataHoraEntrega()).isNull();

        verify(repository, times(1)).persistir(any(Entrega.class));
        verify(repository, times(1)).buscarPorId(any(UUID.class));
    }

    @Test
    void deveLancarExcecaoAoAtualizarSituacaoQuandoJaFoiEntregue() {
        Entrega entrega = EntregaMock.getEntregaJaEntregue();
        when(repository.buscarPorId(any(UUID.class))).thenReturn(entrega);

        assertThatThrownBy(() -> service.atualizarSituacao(UUID.randomUUID(), SituacaoEnum.ENTRADA_FILIAL.getValor()))
                .isInstanceOf(ControllerNotFoundException.class)
                .hasMessage("Não é possível alterar a situação da entrega, pois a entrega foi concluída");

        verify(repository, times(0)).persistir(any(Entrega.class));
        verify(repository, times(1)).buscarPorId(any(UUID.class));
    }

    @Test
    void deveLancarExcecaoAoAtualizarSituacaoParaSituacaoQueNaoForProximaEtapa() {
        Entrega entrega = EntregaMock.getEntregaRecebidaEmFilial();
        when(repository.buscarPorId(any(UUID.class))).thenReturn(entrega);

        assertThatThrownBy(() -> service.atualizarSituacao(UUID.randomUUID(), SituacaoEnum.ENTREGUE.getValor()))
                .isInstanceOf(ControllerNotFoundException.class)
                .hasMessage(String.format("A situação: %s não corresponde a próxima etapa", SituacaoEnum.ENTREGUE.getValor()));

        verify(repository, times(0)).persistir(any(Entrega.class));
        verify(repository, times(1)).buscarPorId(any(UUID.class));
    }

    @Test
    void deveLancarExcecaoAoAtualizarSituacaoParaRotaDeEntregaSemEntregadorAtribuido() {
        Entrega entrega = EntregaMock.getEntregaSemEntregadorAtribuido();
        when(repository.buscarPorId(any(UUID.class))).thenReturn(entrega);

        assertThatThrownBy(() -> service.atualizarSituacao(UUID.randomUUID(), SituacaoEnum.EM_ROTA_ENTREGA.getValor()))
                .isInstanceOf(ControllerNotFoundException.class)
                .hasMessage(String.format("Não é permitido avançar situação para %s sem entregador atribuído", SituacaoEnum.EM_ROTA_ENTREGA.getValor()));

        verify(repository, times(0)).persistir(any(Entrega.class));
        verify(repository, times(1)).buscarPorId(any(UUID.class));
    }

    @Test
    void deveAtribuirEntregadorComSucesso() {
        Entrega entrega = EntregaMock.getEntregaSemEntregadorAtribuido();
        when(repository.buscarPorId(any(UUID.class))).thenReturn(entrega);
        when(repository.persistir(any(Entrega.class))).thenReturn(entrega);
        EntregaResponseDto dtoRetorno = service.atribuirEntregador(UUID.randomUUID(), "Matheus");

        assertThat(dtoRetorno).isNotNull();
        assertThat(dtoRetorno.situacao()).isNotNull().isEqualTo(SituacaoEnum.ENTRADA_FILIAL);
        assertThat(dtoRetorno.entregador()).isNotNull().isEqualTo("Matheus");

        verify(repository, times(1)).persistir(any(Entrega.class));
        verify(repository, times(1)).buscarPorId(any(UUID.class));
    }

    @Test
    void deveLancarExcecaoAoAtribuirEntregadorComEntregaEmRotaDeEntrega() {
        Entrega entrega = EntregaMock.getEntregaEmRotaDeEntrega();
        when(repository.buscarPorId(any(UUID.class))).thenReturn(entrega);

        assertThatThrownBy(() -> service.atribuirEntregador(UUID.randomUUID(), "Matheus"))
                .isInstanceOf(ControllerNotFoundException.class)
                .hasMessage(String.format("Não é permitido alterar entregador com a entrega %s ou %s",
                        SituacaoEnum.ENTREGUE.getValor(), SituacaoEnum.EM_ROTA_ENTREGA.getValor()));

        verify(repository, times(0)).persistir(any(Entrega.class));
        verify(repository, times(1)).buscarPorId(any(UUID.class));
    }

    @Test
    void deveLancarExcecaoAoAtribuirEntregadorQuandoEntregaJaTerEntregadorAtribuido() {
        Entrega entrega = EntregaMock.getEntregaRecebidaEmFilial();
        when(repository.buscarPorId(any(UUID.class))).thenReturn(entrega);

        assertThatThrownBy(() -> service.atribuirEntregador(UUID.randomUUID(), "João"))
                .isInstanceOf(ControllerNotFoundException.class)
                .hasMessage(String.format("Já existe entregador atribuído a entrega: %s", entrega.getEntregador()));

        verify(repository, times(0)).persistir(any(Entrega.class));
        verify(repository, times(1)).buscarPorId(any(UUID.class));
    }

    @Test
    void deveBuscarEntregaPorCodigoRastreioComSucesso() {
        Entrega entrega = EntregaMock.getEntregaJaEntregue();
        when(repository.buscarPorCodigoRastreio(any(String.class))).thenReturn(entrega);
        EntregaResponseDto retornoDto = service.buscarEntregaPorCodigoRastreio("ENT2024112501380610266960344023584573");

        assertThat(retornoDto).isNotNull();
        assertThat(retornoDto.enderecoEntregaDto()).isNotNull();
        assertThat(retornoDto.situacao()).isEqualTo(SituacaoEnum.ENTREGUE);
        assertThat(retornoDto.codigoRastreio()).isNotNull().isEqualTo("ENT2024112501380610266960344023584573");
        assertThat(retornoDto.loteId()).isNotNull();

        verify(repository, times(1)).buscarPorCodigoRastreio(any(String.class));
    }

    @Test
    void deveAtualizarLocalizacaoComSucesso() {
        Entrega entrega = EntregaMock.getEntregaEmRotaDeEntrega();
        LocalizacaoDto localizacaoDto = LocalizacaoDtoMock.getLocalizacaoDto();

        when(repository.buscarPorId(any(UUID.class))).thenReturn(entrega);
        when(repository.persistir(any(Entrega.class))).thenReturn(entrega);
        EntregaResponseDto retornoDto = service.atualizarLocalizacaoEntrega(UUID.randomUUID(), localizacaoDto);

        assertThat(retornoDto).isNotNull();
        assertThat(retornoDto.localizacaoDto()).isNotNull();
        assertThat(retornoDto.localizacaoDto().latitude()).isNotNull().isEqualTo("-20.820878");
        assertThat(retornoDto.localizacaoDto().longitude()).isNotNull().isEqualTo("-49.514063");

        verify(repository, times(1)).persistir(any(Entrega.class));
        verify(repository, times(1)).buscarPorId(any(UUID.class));
    }
}