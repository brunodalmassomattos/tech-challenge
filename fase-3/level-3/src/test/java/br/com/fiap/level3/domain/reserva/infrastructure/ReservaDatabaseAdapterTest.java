package br.com.fiap.level3.domain.reserva.infrastructure;

import br.com.fiap.level3.domain.reserva.core.model.enums.StatusEnum;
import br.com.fiap.level3.domain.reserva.core.model.reserva.Reserva;
import br.com.fiap.level3.domain.reserva.core.model.restaurante.Restaurante;
import br.com.fiap.level3.domain.reserva.core.model.usuario.Usuario;
import br.com.fiap.level3.domain.reserva.mocks.ReservaTestMock;
import br.com.fiap.level3.domain.reserva.mocks.RestauranteTestMock;
import br.com.fiap.level3.domain.reserva.mocks.UsuarioTestMock;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ReservaDatabaseAdapterTest {

    private ReservaDatabaseAdapter reservaDatabaseAdapter;

    @Mock
    private EntityManager entityManager;

    @Mock
    TypedQuery<Reserva> queryReserva;

    private AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        reservaDatabaseAdapter = new ReservaDatabaseAdapter(entityManager);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveSalvarReserva() {
        Reserva reserva = ReservaTestMock.getReserva();
        doNothing().when(entityManager).persist(reserva);

        Reserva reservaCriada = reservaDatabaseAdapter.save(reserva);

        verify(entityManager, times(1)).persist(reserva);
        assertThat(reservaCriada)
                .isNotNull()
                .isInstanceOf(Reserva.class)
                .isEqualTo(reserva);
    }

    @Test
    void deveRetornarReservaParaUsuarioEDataInformada() {
        Reserva reserva = ReservaTestMock.getReserva();

        when(entityManager.createQuery(any(String.class), ArgumentMatchers.<Class<Reserva>>any())).thenReturn(queryReserva);
        when(queryReserva.setParameter("usuarioId", reserva.getUsuario().getId())).thenReturn(queryReserva);
        when(queryReserva.setParameter("data", reserva.getData())).thenReturn(queryReserva);
        when(queryReserva.setParameter("reservasNaoFinalizadas",StatusEnum.getStatusNaoFinalizados())).thenReturn(queryReserva);
        when(queryReserva.getSingleResult()).thenReturn(reserva);

        Optional<Reserva> reservaEcontrada = reservaDatabaseAdapter.getReservaAbertaByUsuarioAndData(
                reserva.getUsuario().getId(), reserva.getData());

        assertThat(reservaEcontrada).isNotEmpty().contains(reserva);
        verify(entityManager, times(1))
                .createQuery(any(String.class), ArgumentMatchers.<Class<Reserva>>any());
    }

    @Test
    void deveRetornarEmptyAoBuscarReservaParaUsuarioEDataInformada() {
        Reserva reserva = ReservaTestMock.getReserva();

        when(entityManager.createQuery(any(String.class), ArgumentMatchers.<Class<Reserva>>any())).thenReturn(queryReserva);
        when(queryReserva.setParameter("usuarioId", reserva.getUsuario().getId())).thenReturn(queryReserva);
        when(queryReserva.setParameter("data", reserva.getData())).thenReturn(queryReserva);
        when(queryReserva.setParameter("reservasNaoFinalizadas",StatusEnum.getStatusNaoFinalizados())).thenReturn(queryReserva);
        when(queryReserva.getSingleResult()).thenReturn(null);

        Optional<Reserva> reservaEcontrada = reservaDatabaseAdapter.getReservaAbertaByUsuarioAndData(
                reserva.getUsuario().getId(), reserva.getData());

        assertThat(reservaEcontrada).isEmpty();
        verify(entityManager, times(1))
                .createQuery(any(String.class), ArgumentMatchers.<Class<Reserva>>any());
    }

    @Test
    void deveRetornarQuantidadeDeLugaresReservadosParaRestauranteInformado() {
        Reserva reserva = ReservaTestMock.getReserva();

        TypedQuery<Long> query = mock(TypedQuery.class);

        when(entityManager.createQuery(any(String.class), ArgumentMatchers.<Class<Long>>any())).thenReturn(query);
        when(query.setParameter("restauranteId", reserva.getRestaurante().getId())).thenReturn(query);
        when(query.setParameter("reservasNaoFinalizadas",StatusEnum.getStatusNaoFinalizados())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(Long.valueOf(80));

        Optional<Long> reservaEcontrada = reservaDatabaseAdapter.getQuantidadeLugaresReservadosByRestaurante(reserva.getRestaurante().getId());

        assertThat(reservaEcontrada).isNotEmpty().get().isInstanceOf(Long.class).isEqualTo(80L);
        verify(entityManager, times(1))
                .createQuery(any(String.class), ArgumentMatchers.<Class<Long>>any());
    }

    @Test
    void deveRetornarUsuarioAoBuscarPorUsuarioId() {
        Usuario usuario = UsuarioTestMock.getUsuario();

        when(entityManager.find(ArgumentMatchers.<Class<Usuario>>any(), any(UUID.class))).thenReturn(usuario);
        Optional<Usuario> usuarioEncontrado = reservaDatabaseAdapter.getUsuarioById(usuario.getId());

        assertThat(usuarioEncontrado).isNotEmpty().contains(usuario);
        verify(entityManager, times(1)).find(ArgumentMatchers.<Class<Usuario>>any(), any(UUID.class));
    }

    @Test
    void deveRetornarEmptyAoBuscarPorUsuarioId() {
        Usuario usuario = UsuarioTestMock.getUsuario();

        when(entityManager.find(ArgumentMatchers.<Class<Usuario>>any(), any(UUID.class))).thenReturn(null);
        Optional<Usuario> usuarioEncontrado = reservaDatabaseAdapter.getUsuarioById(usuario.getId());

        assertThat(usuarioEncontrado).isEmpty();
        verify(entityManager, times(1)).find(ArgumentMatchers.<Class<Usuario>>any(), any(UUID.class));
    }

    @Test
    void deveRetornarRestauranteAoBuscarPorRestauranteId() {
        Restaurante restaurante = RestauranteTestMock.getRestaurante();

        when(entityManager.find(ArgumentMatchers.<Class<Restaurante>>any(), any(UUID.class))).thenReturn(restaurante);
        Optional<Restaurante> restauranteEncontrado = reservaDatabaseAdapter.getRestauranteById(restaurante.getId());

        assertThat(restauranteEncontrado).isNotEmpty().contains(restaurante);
        verify(entityManager, times(1)).find(ArgumentMatchers.<Class<Restaurante>>any(), any(UUID.class));
    }

    @Test
    void deveRetornarEmptyAoBuscarPorRestauranteId() {
        Restaurante restaurante = RestauranteTestMock.getRestaurante();

        when(entityManager.find(ArgumentMatchers.<Class<Restaurante>>any(), any(UUID.class))).thenReturn(null);
        Optional<Restaurante> restauranteEncontrado = reservaDatabaseAdapter.getRestauranteById(restaurante.getId());

        assertThat(restauranteEncontrado).isEmpty();
        verify(entityManager, times(1)).find(ArgumentMatchers.<Class<Restaurante>>any(), any(UUID.class));
    }
}
