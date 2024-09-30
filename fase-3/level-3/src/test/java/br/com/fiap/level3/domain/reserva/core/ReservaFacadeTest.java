package br.com.fiap.level3.domain.reserva.core;

import br.com.fiap.level3.domain.exception.ControllerNotFoundException;
import br.com.fiap.level3.domain.reserva.core.model.enums.StatusEnum;
import br.com.fiap.level3.domain.reserva.core.model.reserva.Reserva;
import br.com.fiap.level3.domain.reserva.core.model.reserva.ReservaDTO;
import br.com.fiap.level3.domain.reserva.core.ports.outgoing.ReservaDatabase;
import br.com.fiap.level3.domain.reserva.mocks.ReservaDTOTestMock;
import br.com.fiap.level3.domain.reserva.mocks.RestauranteTestMock;
import br.com.fiap.level3.domain.reserva.mocks.UsuarioTestMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import br.com.fiap.level3.domain.reserva.core.model.restaurante.RestauranteReserva;
import java.time.LocalTime;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import br.com.fiap.level3.domain.reserva.core.model.reserva.ReservaRestauranteDTO;
import java.util.Arrays;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import br.com.fiap.level3.domain.reserva.core.model.usuario.Usuario;

public class ReservaFacadeTest {

    private ReservaFacade reservaFacade;

    @Mock
    private ReservaDatabase reservaDatabase;

    private AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        reservaFacade = new ReservaFacade(reservaDatabase);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveCriarNovaReserva() {
        ReservaDTO novaReserva = ReservaDTOTestMock.getReservaDTO();

        when(reservaDatabase.getUsuarioById(any(UUID.class)))
                .thenReturn(Optional.of(UsuarioTestMock.getUsuario()));
        when(reservaDatabase.getRestauranteById(any(UUID.class)))
                .thenReturn(Optional.of(RestauranteTestMock.getRestaurante()));
        when(reservaDatabase.getReservaAbertaByUsuarioAndData(any(UUID.class), any(LocalDate.class)))
                .thenReturn(Optional.empty());
        when(reservaDatabase.getQuantidadeLugaresReservadosByRestaurante(any(UUID.class)))
                .thenReturn(Optional.of(10L));
        when(reservaDatabase.save(any(Reserva.class)))
                .thenAnswer(p -> p.getArgument(0));

        ReservaDTO reservaCriada = reservaFacade.createNewReserva(novaReserva);

        assertThat(reservaCriada.status())
                .isNotNull()
                .isEqualTo(StatusEnum.CRIADA.getDescricao());
        assertThat(reservaCriada.usuarioId())
                .isNotNull()
                .isEqualTo(UsuarioTestMock.getUsuario().getId());
        assertThat(reservaCriada.restauranteId())
                .isNotNull()
                .isEqualTo(RestauranteTestMock.getRestaurante().getId());

        verify(reservaDatabase, times(1)).save(any(Reserva.class));
    }

    @Test
    void deveLancarExcecaoAoCriarNovaReservaEUsuarioTerReservaAbertaParaMesmoDia() {
        ReservaDTO novaReserva = ReservaDTOTestMock.getReservaDTO();

        when(reservaDatabase.getUsuarioById(any(UUID.class)))
                .thenReturn(Optional.of(UsuarioTestMock.getUsuario()));
        when(reservaDatabase.getRestauranteById(any(UUID.class)))
                .thenReturn(Optional.of(RestauranteTestMock.getRestaurante()));
        when(reservaDatabase.getReservaAbertaByUsuarioAndData(any(UUID.class), any(LocalDate.class)))
                .thenReturn(Optional.of(Reserva.builder().build()));

        assertThatThrownBy(() -> reservaFacade.createNewReserva(novaReserva))
                .isInstanceOf(ControllerNotFoundException.class)
                .hasMessage("Existe uma reserva aberta no mesmo dia para o usuário: Vitor Joaquim Leandro Lopes");

        verify(reservaDatabase, times(1)).getReservaAbertaByUsuarioAndData(any(UUID.class), any(LocalDate.class));
    }

    @Test
    void deveLancarExcecaoAoCriarReservaNovaEQuantidadePessoasUltrapassarCapacidadeDoRestaurante() {
        ReservaDTO novaReserva = ReservaDTOTestMock.getReservaDTO();

        when(reservaDatabase.getUsuarioById(any(UUID.class)))
                .thenReturn(Optional.of(UsuarioTestMock.getUsuario()));
        when(reservaDatabase.getRestauranteById(any(UUID.class)))
                .thenReturn(Optional.of(RestauranteTestMock.getRestaurante()));
        when(reservaDatabase.getReservaAbertaByUsuarioAndData(any(UUID.class), any(LocalDate.class)))
                .thenReturn(Optional.empty());
        when(reservaDatabase.getQuantidadeLugaresReservadosByRestaurante(any(UUID.class)))
                .thenReturn(Optional.of(97L));

        assertThatThrownBy(() -> reservaFacade.createNewReserva(novaReserva))
                .isInstanceOf(ControllerNotFoundException.class)
                .hasMessage("Atingiu a capacidade do restaurante, restam apenas 3 lugares");

        verify(reservaDatabase, times(1)).getQuantidadeLugaresReservadosByRestaurante(any(UUID.class));
    }

    @Test
    void deveLancarExcecaoAoCriarNovaReservaENaoEncontrarUsuario() {

        ReservaDTO novaReserva = ReservaDTOTestMock.getReservaDTO();

        when(reservaDatabase.getUsuarioById(novaReserva.usuarioId()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> reservaFacade.createNewReserva(novaReserva))
                .isInstanceOf(ControllerNotFoundException.class)
                .hasMessage(String.format("Usuário não encontrado para o ID: %s", novaReserva.usuarioId()));
        verify(reservaDatabase, times(1)).getUsuarioById(novaReserva.usuarioId());
    }

    @Test
    void deveLancarExcecaoAoCriarNovaReservaENaoEncontrarRestaurante() {

        ReservaDTO novaReserva = ReservaDTOTestMock.getReservaDTO();

        when(reservaDatabase.getUsuarioById(any(UUID.class)))
                .thenReturn(Optional.of(UsuarioTestMock.getUsuario()));
        when(reservaDatabase.getRestauranteById(any(UUID.class)))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> reservaFacade.createNewReserva(novaReserva))
                .isInstanceOf(ControllerNotFoundException.class)
                .hasMessage(String.format("Restaurante não encontrado para o ID: %s", novaReserva.restauranteId()));
        verify(reservaDatabase, times(1)).getRestauranteById(any(UUID.class));
    }

    @Test
    void deveLancarExcecaoAoCriarReservaQuandoDataForNulo() {
        ReservaDTO novaReserva = ReservaDTOTestMock.getReservaDTOSemDataInformada();

        when(reservaDatabase.getUsuarioById(any(UUID.class)))
                .thenReturn(Optional.of(UsuarioTestMock.getUsuario()));
        when(reservaDatabase.getRestauranteById(any(UUID.class)))
                .thenReturn(Optional.of(RestauranteTestMock.getRestaurante()));

        assertThatThrownBy(() -> reservaFacade.createNewReserva(novaReserva))
                .isInstanceOf(ControllerNotFoundException.class)
                .hasMessage("É necessário informar uma data para criar reserva!");
    }

    @Test
    void deveLancarExcecaoAoCriarReservaQuandoHoraForNulo() {
        ReservaDTO novaReserva = ReservaDTOTestMock.getReservaDTOSemHoraInformada();

        when(reservaDatabase.getUsuarioById(any(UUID.class)))
                .thenReturn(Optional.of(UsuarioTestMock.getUsuario()));
        when(reservaDatabase.getRestauranteById(any(UUID.class)))
                .thenReturn(Optional.of(RestauranteTestMock.getRestaurante()));

        assertThatThrownBy(() -> reservaFacade.createNewReserva(novaReserva))
                .isInstanceOf(ControllerNotFoundException.class)
                .hasMessage("É necessário informar um horário para criar reserva!");
    }

    @Test
    void deveLancarExcecaoAoCriarReservaQuandoQuantidadePessoasForNulo() {
        ReservaDTO novaReserva = ReservaDTOTestMock.getReservaDTOSemQuantidadeDePessoasInformada();

        when(reservaDatabase.getUsuarioById(any(UUID.class)))
                .thenReturn(Optional.of(UsuarioTestMock.getUsuario()));
        when(reservaDatabase.getRestauranteById(any(UUID.class)))
                .thenReturn(Optional.of(RestauranteTestMock.getRestaurante()));

        assertThatThrownBy(() -> reservaFacade.createNewReserva(novaReserva))
                .isInstanceOf(ControllerNotFoundException.class)
                .hasMessage("Deve haver pelo menos um cliente para criar a reserva!");
    }

    @Test
    void deveLancarExcecaoAoCriarReservaQuandoQuantidadePessoasForMenorQueUm() {
        ReservaDTO novaReserva = ReservaDTOTestMock.getReservaDTOComQuantidadeDePessoasMenorQueUm();

        when(reservaDatabase.getUsuarioById(any(UUID.class)))
                .thenReturn(Optional.of(UsuarioTestMock.getUsuario()));
        when(reservaDatabase.getRestauranteById(any(UUID.class)))
                .thenReturn(Optional.of(RestauranteTestMock.getRestaurante()));

        assertThatThrownBy(() -> reservaFacade.createNewReserva(novaReserva))
                .isInstanceOf(ControllerNotFoundException.class)
                .hasMessage("Deve haver pelo menos um cliente para criar a reserva!");
    }

    @Test
    void deveLancarExcecaoAoCriarReservaQuandoDataForAntesDaAtual() {
        ReservaDTO novaReserva = ReservaDTOTestMock.getReservaDTOComDataAnteriorQueAtual();

        when(reservaDatabase.getUsuarioById(any(UUID.class)))
                .thenReturn(Optional.of(UsuarioTestMock.getUsuario()));
        when(reservaDatabase.getRestauranteById(any(UUID.class)))
                .thenReturn(Optional.of(RestauranteTestMock.getRestaurante()));

        assertThatThrownBy(() -> reservaFacade.createNewReserva(novaReserva))
                .isInstanceOf(ControllerNotFoundException.class)
                .hasMessage("A data informada é menor que a data atual!");
    }

    @Test
    void deveLancarExcecaoQuandoDataIgualDataAtualEIntervaloDeHoraAtualEHoraMenorQue30Minutos() {
        ReservaDTO novaReserva = ReservaDTOTestMock.getReservaDTOComDataIgualAtualEHoraIgualHoraAtual();

        when(reservaDatabase.getUsuarioById(any(UUID.class)))
                .thenReturn(Optional.of(UsuarioTestMock.getUsuario()));
        when(reservaDatabase.getRestauranteById(any(UUID.class)))
                .thenReturn(Optional.of(RestauranteTestMock.getRestaurante()));

        assertThatThrownBy(() -> reservaFacade.createNewReserva(novaReserva))
                .isInstanceOf(ControllerNotFoundException.class)
                .hasMessage("Deve ser informado horário com pelo menos 30 minutos de antececência!");
    }

    @Test
    void listarReservasPorRestaurante_Success() {
        // Arrange
        UUID restauranteId = UUID.randomUUID();
        RestauranteReserva restaurante = new RestauranteReserva(restauranteId, "Test Restaurant", 10);
        Reserva reserva1 = new Reserva(UUID.randomUUID(), LocalDate.now(), LocalTime.now(), 2, restaurante, new Usuario(), StatusEnum.CRIADA.getDescricao());
        Reserva reserva2 = new Reserva(UUID.randomUUID(), LocalDate.now(), LocalTime.now().plusHours(1), 3, restaurante, new Usuario(), StatusEnum.CRIADA.getDescricao());

        when(reservaDatabase.listarReservasPorRestaurante(restauranteId)).thenReturn(Arrays.asList(reserva1, reserva2));
        when(reservaDatabase.getCapacidadeRestaurante(restauranteId)).thenReturn(10);

        // Act
        ReservaRestauranteDTO result = reservaFacade.listarReservasPorRestaurante(restauranteId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.reservas().size());
        assertEquals(5, result.totalPessoas());
        assertEquals(10, result.capacidadeRestaurante());
        assertTrue(result.podeAceitarMaisReservas());
    }

    @Test
    void listarReservasPorRestaurante_EmptyList() {
        // Arrange
        UUID restauranteId = UUID.randomUUID();
        when(reservaDatabase.listarReservasPorRestaurante(restauranteId)).thenReturn(Arrays.asList());
        when(reservaDatabase.getCapacidadeRestaurante(restauranteId)).thenReturn(10);

        // Act
        ReservaRestauranteDTO result = reservaFacade.listarReservasPorRestaurante(restauranteId);

        // Assert
        assertNotNull(result);
        assertTrue(result.reservas().isEmpty());
        assertEquals(0, result.totalPessoas());
        assertEquals(10, result.capacidadeRestaurante());
        assertTrue(result.podeAceitarMaisReservas());
    }

    @Test
    void listarReservasPorRestaurante_RestauranteNaoExiste() {
        // Arrange
        UUID restauranteId = UUID.randomUUID();
        when(reservaDatabase.listarReservasPorRestaurante(restauranteId)).thenReturn(Collections.emptyList());
        when(reservaDatabase.getCapacidadeRestaurante(restauranteId)).thenReturn(0);

        // Act
        ReservaRestauranteDTO result = reservaFacade.listarReservasPorRestaurante(restauranteId);

        // Assert
        assertNotNull(result);
        assertTrue(result.reservas().isEmpty());
        assertEquals(0, result.totalPessoas());
        assertEquals(0, result.capacidadeRestaurante());
        assertFalse(result.podeAceitarMaisReservas()); // Mudamos para assertFalse
    }

    @Test
    void listarReservasPorRestaurante_CapacidadeMaximaAtingida() {
        // Arrange
        UUID restauranteId = UUID.randomUUID();
        RestauranteReserva restaurante = new RestauranteReserva(restauranteId, "Test Restaurant", 10);
        Reserva reserva = new Reserva(UUID.randomUUID(), LocalDate.now(), LocalTime.now(), 10, restaurante, new Usuario(), StatusEnum.CRIADA.getDescricao());

        when(reservaDatabase.listarReservasPorRestaurante(restauranteId)).thenReturn(Collections.singletonList(reserva));
        when(reservaDatabase.getCapacidadeRestaurante(restauranteId)).thenReturn(10);

        // Act
        ReservaRestauranteDTO result = reservaFacade.listarReservasPorRestaurante(restauranteId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.reservas().size());
        assertEquals(10, result.totalPessoas());
        assertEquals(10, result.capacidadeRestaurante());
        assertFalse(result.podeAceitarMaisReservas());
    }

    @Test
    void listarReservaPorId_Success() {
        // Arrange
        UUID reservaId = UUID.randomUUID();
        Reserva reserva = new Reserva(reservaId, LocalDate.now(), LocalTime.now(), 2, new RestauranteReserva(), new Usuario(), StatusEnum.CRIADA.getDescricao());

        when(reservaDatabase.getReservaPorId(reservaId)).thenReturn(Optional.of(reserva));

        // Act
        Optional<ReservaDTO> result = reservaFacade.listarReservaPorId(reservaId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(reservaId, result.get().id()); // Comparando UUID diretamente
        assertEquals(StatusEnum.CRIADA.getDescricao(), result.get().status());
    }

    @Test
    void listarReservaPorId_NotFound() {
        // Arrange
        UUID reservaId = UUID.randomUUID();
        when(reservaDatabase.getReservaPorId(reservaId)).thenReturn(Optional.empty());

        // Act
        Optional<ReservaDTO> result = reservaFacade.listarReservaPorId(reservaId);

        // Assert
        assertFalse(result.isPresent());
    }
    @Test
    void listarReservaPorId_ReservaComStatusCancelado() {
        // Arrange
        UUID reservaId = UUID.randomUUID();
        Reserva reserva = new Reserva(reservaId, LocalDate.now(), LocalTime.now(), 2, new RestauranteReserva(), new Usuario(), StatusEnum.CANCELADA.getDescricao());

        when(reservaDatabase.getReservaPorId(reservaId)).thenReturn(Optional.of(reserva));

        // Act
        Optional<ReservaDTO> result = reservaFacade.listarReservaPorId(reservaId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(reservaId, result.get().id());
        assertEquals(StatusEnum.CANCELADA.getDescricao(), result.get().status());
    }

    @Test
    void listarReservaPorId_ReservaComDataPassada() {
        // Arrange
        UUID reservaId = UUID.randomUUID();
        Reserva reserva = new Reserva(reservaId, LocalDate.now().minusDays(1), LocalTime.now(), 2, new RestauranteReserva(), new Usuario(), StatusEnum.CONCLUIDA.getDescricao());

        when(reservaDatabase.getReservaPorId(reservaId)).thenReturn(Optional.of(reserva));

        // Act
        Optional<ReservaDTO> result = reservaFacade.listarReservaPorId(reservaId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(reservaId, result.get().id());
        assertEquals(StatusEnum.CONCLUIDA.getDescricao(), result.get().status());
        assertTrue(LocalDate.parse(result.get().data()).isBefore(LocalDate.now()));
    }


    @Test
    void atualizarStatusReserva_Success() {
        // Arrange
        UUID reservaId = UUID.randomUUID();
        Reserva reserva = new Reserva(reservaId, LocalDate.now(), LocalTime.now(), 2, new RestauranteReserva(), new Usuario(), StatusEnum.CRIADA.getDescricao());
        Reserva updatedReserva = new Reserva(reservaId, LocalDate.now(), LocalTime.now(), 2, new RestauranteReserva(), new Usuario(), StatusEnum.CONFIRMADA.getDescricao());

        when(reservaDatabase.atualizarStatusReserva(reservaId, StatusEnum.CONFIRMADA)).thenReturn(Optional.of(updatedReserva));

        // Act
        Optional<ReservaDTO> result = reservaFacade.atualizarStatusReserva(reservaId, StatusEnum.CONFIRMADA);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(StatusEnum.CONFIRMADA.getDescricao(), result.get().status());
    }

    @Test
    void atualizarStatusReserva_NotFound() {
        // Arrange
        UUID reservaId = UUID.randomUUID();
        when(reservaDatabase.atualizarStatusReserva(reservaId, StatusEnum.CONFIRMADA)).thenReturn(Optional.empty());

        // Act
        Optional<ReservaDTO> result = reservaFacade.atualizarStatusReserva(reservaId, StatusEnum.CONFIRMADA);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void atualizarStatusReserva_DeConfirmadaParaCancelada() {
        // Arrange
        UUID reservaId = UUID.randomUUID();
        Reserva reservaOriginal = new Reserva(reservaId, LocalDate.now(), LocalTime.now(), 2, new RestauranteReserva(), new Usuario(), StatusEnum.CONFIRMADA.getDescricao());
        Reserva reservaAtualizada = new Reserva(reservaId, LocalDate.now(), LocalTime.now(), 2, new RestauranteReserva(), new Usuario(), StatusEnum.CANCELADA.getDescricao());

        when(reservaDatabase.atualizarStatusReserva(reservaId, StatusEnum.CANCELADA)).thenReturn(Optional.of(reservaAtualizada));

        // Act
        Optional<ReservaDTO> result = reservaFacade.atualizarStatusReserva(reservaId, StatusEnum.CANCELADA);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(StatusEnum.CANCELADA.getDescricao(), result.get().status());
    }

    @Test
    void atualizarStatusReserva_ParaStatusInvalido() {
        // Arrange
        UUID reservaId = UUID.randomUUID();
        when(reservaDatabase.atualizarStatusReserva(reservaId, null)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> reservaFacade.atualizarStatusReserva(reservaId, null));
    }

    @Test
    void atualizarStatusReserva_ReservaNaoExistente() {
        // Arrange
        UUID reservaId = UUID.randomUUID();
        when(reservaDatabase.atualizarStatusReserva(reservaId, StatusEnum.CONFIRMADA)).thenReturn(Optional.empty());

        // Act
        Optional<ReservaDTO> result = reservaFacade.atualizarStatusReserva(reservaId, StatusEnum.CONFIRMADA);

        // Assert
        assertFalse(result.isPresent());
    }
}
