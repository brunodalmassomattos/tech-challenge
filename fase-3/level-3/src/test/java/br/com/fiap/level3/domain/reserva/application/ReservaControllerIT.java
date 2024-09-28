package br.com.fiap.level3.domain.reserva.application;

import br.com.fiap.level3.domain.reserva.core.model.reserva.ReservaDTO;
import br.com.fiap.level3.domain.reserva.mocks.ReservaDTOTestMock;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_CLASS;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = {"/reserva.sql"}, executionPhase = BEFORE_TEST_CLASS)
@AutoConfigureTestDatabase
public class ReservaControllerIT {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void deveCriarNovaReserva() throws Exception {
        ReservaDTO novaReserva = ReservaDTOTestMock.getReservaDTO();

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(novaReserva)
                .when()
                .post("/reservas")
                .then().statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void deveLancarExcecaoAoCriarNovaReservaEUsuarioTerReservaAbertaParaMesmoDia() {
        ReservaDTO novaReserva = ReservaDTOTestMock.reservaDTOBuilder()
                                         .data(LocalDate.now().format(DateTimeFormatter.ISO_DATE))
                                         .hora(LocalTime.now().plusHours(2).format(DateTimeFormatter.ofPattern("HH:mm")))
                                         .build();

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(novaReserva)
                .when()
                .post("/reservas")
                .then().statusCode(HttpStatus.NOT_FOUND.value())
                .body("$", hasKey("error"))
                .body("error", equalTo("Entity not found"))
                .body("message", equalTo("Existe uma reserva aberta no mesmo dia para o usuário: Vitor Joaquim Leandro Lopes"));
    }

    @Test
    void deveLancarExcecaoAoCriarReservaNovaEQuantidadePessoasUltrapassarCapacidadeDoRestaurante() {
        ReservaDTO novaReserva = ReservaDTOTestMock.reservaDTOBuilder()
                                         .usuarioId(UUID.fromString("da09571d-9409-418a-8f49-d354552a6acb"))
                                         .restauranteId(UUID.fromString("e0c65eb4-cb52-4df0-a582-44bc8ab756fb"))
                                         .build();

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(novaReserva)
                .when()
                .post("/reservas")
                .then().statusCode(HttpStatus.NOT_FOUND.value())
                .body("$", hasKey("error"))
                .body("error", equalTo("Entity not found"))
                .body("message", equalTo("Atingiu a capacidade do restaurante, restam apenas 3 lugares"));
    }

    @Test
    void deveLancarExcecaoAoCriarNovaReservaENaoEncontrarUsuario() {
        ReservaDTO novaReserva = ReservaDTOTestMock.reservaDTOBuilder()
                                         .usuarioId(UUID.randomUUID())
                                         .build();

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(novaReserva)
                .when()
                .post("/reservas")
                .then().statusCode(HttpStatus.NOT_FOUND.value())
                .body("$", hasKey("error"))
                .body("error", equalTo("Entity not found"))
                .body("message", equalTo(String.format("Usuário não encontrado para o ID: %s", novaReserva.usuarioId())));
    }

    @Test
    void deveLancarExcecaoAoCriarNovaReservaENaoEncontrarRestaurante() {

        ReservaDTO novaReserva = ReservaDTOTestMock.reservaDTOBuilder()
                                         .restauranteId(UUID.randomUUID())
                                         .build();

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(novaReserva)
                .when()
                .post("/reservas")
                .then().statusCode(HttpStatus.NOT_FOUND.value())
                .body("$", hasKey("error"))
                .body("error", equalTo("Entity not found"))
                .body("message", equalTo(String.format("Restaurante não encontrado para o ID: %s", novaReserva.restauranteId())));
    }

    @Test
    void deveLancarExcecaoAoCriarReservaQuandoDataForNulo() {
        ReservaDTO novaReserva = ReservaDTOTestMock.getReservaDTOSemDataInformada();

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(novaReserva)
                .when()
                .post("/reservas")
                .then().statusCode(HttpStatus.NOT_FOUND.value())
                .body("$", hasKey("error"))
                .body("error", equalTo("Entity not found"))
                .body("message", equalTo("É necessário informar uma data para criar reserva!"));

    }

    @Test
    void deveLancarExcecaoAoCriarReservaQuandoHoraForNulo() {
        ReservaDTO novaReserva = ReservaDTOTestMock.getReservaDTOSemHoraInformada();

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(novaReserva)
                .when()
                .post("/reservas")
                .then().statusCode(HttpStatus.NOT_FOUND.value())
                .body("$", hasKey("error"))
                .body("error", equalTo("Entity not found"))
                .body("message", equalTo("É necessário informar um horário para criar reserva!"));
    }

    @Test
    void deveLancarExcecaoAoCriarReservaQuandoQuantidadePessoasForNulo() {
        ReservaDTO novaReserva = ReservaDTOTestMock.getReservaDTOSemQuantidadeDePessoasInformada();

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(novaReserva)
                .when()
                .post("/reservas")
                .then().statusCode(HttpStatus.NOT_FOUND.value())
                .body("$", hasKey("error"))
                .body("error", equalTo("Entity not found"))
                .body("message", equalTo("Deve haver pelo menos um cliente para criar a reserva!"));
    }

    @Test
    void deveLancarExcecaoAoCriarReservaQuandoQuantidadePessoasForMenorQueUm() {
        ReservaDTO novaReserva = ReservaDTOTestMock.getReservaDTOComQuantidadeDePessoasMenorQueUm();

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(novaReserva)
                .when()
                .post("/reservas")
                .then().statusCode(HttpStatus.NOT_FOUND.value())
                .body("$", hasKey("error"))
                .body("error", equalTo("Entity not found"))
                .body("message", equalTo("Deve haver pelo menos um cliente para criar a reserva!"));
    }

    @Test
    void deveLancarExcecaoAoCriarReservaQuandoDataForAntesDaAtual() {
        ReservaDTO novaReserva = ReservaDTOTestMock.getReservaDTOComDataAnteriorQueAtual();

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(novaReserva)
                .when()
                .post("/reservas")
                .then().statusCode(HttpStatus.NOT_FOUND.value())
                .body("$", hasKey("error"))
                .body("error", equalTo("Entity not found"))
                .body("message", equalTo("A data informada é menor que a data atual!"));
    }

    @Test
    void deveLancarExcecaoQuandoDataIgualDataAtualEIntervaloDeHoraAtualEHoraMenorQue30Minutos() {
        ReservaDTO novaReserva = ReservaDTOTestMock.getReservaDTOComDataIgualAtualEHoraIgualHoraAtual();

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(novaReserva)
                .when()
                .post("/reservas")
                .then().statusCode(HttpStatus.NOT_FOUND.value())
                .body("$", hasKey("error"))
                .body("error", equalTo("Entity not found"))
                .body("message", equalTo("Deve ser informado horário com pelo menos 30 minutos de antececência!"));
    }
}
