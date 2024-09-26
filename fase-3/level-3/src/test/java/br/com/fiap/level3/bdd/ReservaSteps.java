package br.com.fiap.level3.bdd;

import br.com.fiap.level3.domain.reserva.core.model.enums.StatusEnum;
import br.com.fiap.level3.domain.reserva.core.model.reserva.ReservaDTO;
import br.com.fiap.level3.domain.reserva.core.model.reserva.ReservaRestauranteDTO;
import br.com.fiap.level3.domain.reserva.core.ports.incoming.CreateNewReserva;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ReservaSteps {

    @Autowired
    private CreateNewReserva createNewReserva;

    private UUID restauranteId;
    private UUID reservaId;
    private ReservaRestauranteDTO reservasRestaurante;
    private ReservaDTO reservaEncontrada;
    private ReservaDTO reservaAtualizada;

    @Given("um restaurante com ID {string}")
    public void umRestauranteComID(String restauranteIdStr) {
        this.restauranteId = UUID.fromString(restauranteIdStr);
    }

    @When("o sistema lista as reservas para este restaurante")
    public void oSistemaListaAsReservasParaEsteRestaurante() {
        reservasRestaurante = createNewReserva.listarReservasPorRestaurante(restauranteId);
    }

    @Then("o sistema retorna uma lista de reservas")
    public void oSistemaRetornaUmaListaDeReservas() {
        assertNotNull(reservasRestaurante);
        assertFalse(reservasRestaurante.reservas().isEmpty());
    }

    @Then("o total de pessoas é {int}")
    public void oTotalDePessoasE(Integer totalPessoas) {
        assertEquals(totalPessoas, reservasRestaurante.totalPessoas());
    }

    @Then("a capacidade do restaurante é {int}")
    public void aCapacidadeDoRestauranteE(Integer capacidade) {
        assertEquals(capacidade, reservasRestaurante.capacidadeRestaurante());
    }

    @Then("o restaurante pode aceitar mais reservas")
    public void oRestaurantePodeAceitarMaisReservas() {
        assertTrue(reservasRestaurante.podeAceitarMaisReservas());
    }

    @Given("uma reserva existente com ID {string}")
    public void umaReservaExistenteComID(String reservaIdStr) {
        this.reservaId = UUID.fromString(reservaIdStr);
    }

    @When("o sistema busca a reserva por este ID")
    public void oSistemaBuscaAReservaPorEsteID() {
        reservaEncontrada = createNewReserva.listarReservaPorId(reservaId).orElse(null);
    }

    @Then("o sistema retorna os detalhes da reserva")
    public void oSistemaRetornaOsDetalhesDaReserva() {
        assertNotNull(reservaEncontrada);
    }

    @Then("o status da reserva é {string}")
    public void oStatusDaReservaE(String status) {
        assertEquals(status, reservaEncontrada.status());
    }

    @When("o status da reserva é atualizado para {string}")
    public void oStatusDaReservaEAtualizadoPara(String novoStatus) {
        reservaAtualizada = createNewReserva.atualizarStatusReserva(reservaId, StatusEnum.valueOf(novoStatus)).orElse(null);
    }

    @Then("o status da reserva é atualizado com sucesso")
    public void oStatusDaReservaEAtualizadoComSucesso() {
        assertNotNull(reservaAtualizada);
    }

    @Then("o novo status da reserva é {string}")
    public void oNovoStatusDaReservaE(String status) {
        assertEquals(status, reservaAtualizada.status());
    }

    @Given("uma reserva inexistente com ID {string}")
    public void umaReservaInexistenteComID(String reservaIdStr) {
        this.reservaId = UUID.fromString(reservaIdStr);
    }

    @When("o sistema tenta atualizar o status da reserva para {string}")
    public void oSistemaTentaAtualizarOStatusDaReservaPara(String novoStatus) {
        Optional<ReservaDTO> resultado = createNewReserva.atualizarStatusReserva(reservaId, StatusEnum.valueOf(novoStatus));
        reservaAtualizada = resultado.orElse(null);
    }

    @Then("o sistema retorna uma mensagem indicando que a reserva não foi encontrada")
    public void oSistemaRetornaUmaMensagemIndicandoQueAReservaNaoFoiEncontrada() {
        assertNull(reservaAtualizada);
    }
}
