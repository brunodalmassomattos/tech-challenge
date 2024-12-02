package br.com.fiap.entrega.persistence.repository;

import br.com.fiap.entrega.application.exception.ControllerNotFoundException;
import br.com.fiap.entrega.domain.entity.Entrega;
import br.com.fiap.entrega.domain.repository.EntregaRepository;
import br.com.fiap.entrega.mocks.EntregaMock;
import br.com.fiap.entrega.persistence.data.DataEntregaRepository;
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

class EntregaRepositoryImplTest {

    @Mock
    private DataEntregaRepository dataEntregaRepository;

    private EntregaRepository repository;

    private AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        repository = new EntregaRepositoryImpl(dataEntregaRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveBuscarEntregaPorIdComSucesso() {
        when(dataEntregaRepository.findById(any(UUID.class))).thenReturn(Optional.of(EntregaMock.getEntrega()));

        Entrega entrega = repository.buscarPorId(UUID.randomUUID());

        assertThat(entrega).isNotNull();
        assertThat(entrega.getCodigoRastreio()).isEqualTo("ENT2024112501380610266960344023584573");
        verify(dataEntregaRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    void deveLancarExcecaoQuandoNaoEncontrarEntregaPorId() {
        when(dataEntregaRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        UUID entregaId = UUID.randomUUID();
        assertThatThrownBy(() -> repository.buscarPorId(entregaId))
                .isInstanceOf(ControllerNotFoundException.class)
                .hasMessage(String.format("Não existe entrega para o id: %s", entregaId.toString()));

        verify(dataEntregaRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    void deveBuscarEntregaPorCodigoRastreioComSucesso() {
        when(dataEntregaRepository.findByCodigoRastreio(any(String.class))).thenReturn(Optional.of(EntregaMock.getEntrega()));

        Entrega entrega = repository.buscarPorCodigoRastreio("ENT2024112501380610266960344023584573");

        assertThat(entrega).isNotNull();
        assertThat(entrega.getCodigoRastreio()).isEqualTo("ENT2024112501380610266960344023584573");
        verify(dataEntregaRepository, times(1)).findByCodigoRastreio(any(String.class));
    }

    @Test
    void deveLancarExcecaoQuandoNaoEncontrarEntregaPorCodigoRastreio() {
        when(dataEntregaRepository.findByCodigoRastreio(any(String.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> repository.buscarPorCodigoRastreio("ENT2024112501380610266960344023584573"))
                .isInstanceOf(ControllerNotFoundException.class)
                .hasMessage("Não existe entrega para o código: ENT2024112501380610266960344023584573");

        verify(dataEntregaRepository, times(1)).findByCodigoRastreio(any(String.class));
    }
}