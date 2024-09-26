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

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

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
}
