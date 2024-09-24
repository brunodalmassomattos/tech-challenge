package br.com.fiap.level3.domain.restaurante.application;

import br.com.fiap.level3.domain.restaurante.core.ports.incoming.*;
import br.com.fiap.level3.domain.tiporestaurante.core.ports.incoming.FindTipoRestaurante;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"/restaurantes.sql"})
@Sql(scripts = {"/clean.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("test")
class RestauranteControllerTestIT {

    @MockBean
    private FindRestaurante findRestaurante;

    @MockBean
    private AddRestaurante addRestaurante;

    @MockBean
    private AlterRestaurante alterRestaurante;

    @MockBean
    private DeleteRestaurante deleteRestaurante;

    @MockBean
    private AlterEndereco alterEndereco;

    @BeforeEach
    public void setup() {
    }

    @Nested
    class BuscarMensagem {

        @Test
        void devePermitirBuscarMensagem() {
            var id = "ce2f69e4-88c7-4134-91eb-302b0ce8edf5";

            given()
                    .filter(new AllureRestAssured())
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .get("/v1/restaurantes")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body(matchesJsonSchemaInClasspath(""/*""./schemas/MensagemResponseSchema.json"*/));
        }
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}