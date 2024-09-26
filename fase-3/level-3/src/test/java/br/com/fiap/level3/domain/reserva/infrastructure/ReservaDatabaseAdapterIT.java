package br.com.fiap.level3.domain.reserva.infrastructure;


import br.com.fiap.level3.DatabaseHelper;
import br.com.fiap.level3.domain.reserva.core.model.enums.StatusEnum;
import br.com.fiap.level3.domain.reserva.core.model.reserva.Reserva;
import br.com.fiap.level3.domain.reserva.core.model.reserva.ReservaDTO;
import br.com.fiap.level3.domain.reserva.core.model.restaurante.Restaurante;
import br.com.fiap.level3.domain.reserva.core.model.usuario.Usuario;
import br.com.fiap.level3.domain.reserva.mocks.ReservaDTOTestMock;
import br.com.fiap.level3.domain.reserva.mocks.ReservaTestMock;
import br.com.fiap.level3.domain.reserva.mocks.RestauranteTestMock;
import br.com.fiap.level3.domain.reserva.mocks.UsuarioTestMock;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase
public class ReservaDatabaseAdapterIT {

    @Autowired
    private EntityManager entityManager;

    private ReservaDatabaseAdapter reservaDatabaseAdapter;

    private DatabaseHelper databaseHelper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        reservaDatabaseAdapter = new ReservaDatabaseAdapter(entityManager);
        databaseHelper = new DatabaseHelper(jdbcTemplate);
    }

    @Test
    void deveSalvarReserva() {
        Reserva reserva = ReservaTestMock.getReserva();
        Reserva reservaSalva = reservaDatabaseAdapter.save(reserva);
        assertThat(reservaSalva).isNotNull().extracting(Reserva::getId).isNotNull();
    }

    @Test
    @Sql({"/reserva.sql"})
    void deveRetornarReservaParaUsuarioEDataInformada() {
        LocalDate data = LocalDate.of(2024, 9, 24);
        UUID usuarioId = UUID.fromString("599abd45-3f86-44b6-8837-fc16f130944e");
        Optional<Reserva> reservaEncontrada = reservaDatabaseAdapter.getReservaAbertaByUsuarioAndData(usuarioId, data);
        assertThat(reservaEncontrada).isPresent().get().extracting(Reserva::getId).isNotNull();
    }

    @Test
    @Sql(scripts = "/reserva.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deveRetornarQuantidadeDeLugaresReservadosParaRestauranteInformado() {
        UUID restauranteId = UUID.fromString("e0c65eb4-cb52-4df0-a582-44bc8ab756fb");
        Optional<Long> reservaEncontrada = reservaDatabaseAdapter.getQuantidadeLugaresReservadosByRestaurante(restauranteId);
        assertThat(reservaEncontrada).isPresent().get().isEqualTo(5L);
    }
}
