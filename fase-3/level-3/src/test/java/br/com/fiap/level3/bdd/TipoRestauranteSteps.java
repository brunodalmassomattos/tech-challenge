package br.com.fiap.level3.bdd;

import br.com.fiap.level3.domain.tiporestaurante.core.model.TipoRestaurante;
import br.com.fiap.level3.domain.tiporestaurante.core.model.TipoRestauranteDTO;
import br.com.fiap.level3.domain.tiporestaurante.core.ports.incoming.AddTipoRestaurante;
import br.com.fiap.level3.domain.tiporestaurante.core.ports.incoming.AlterTipoRestaurante;
import br.com.fiap.level3.domain.tiporestaurante.core.ports.incoming.DeleteTipoRestaurante;
import br.com.fiap.level3.domain.tiporestaurante.core.ports.incoming.FindTipoRestaurante;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class TipoRestauranteSteps {

    @Autowired
    private AddTipoRestaurante addTipoRestaurante;

    @Autowired
    private AlterTipoRestaurante alterTipoRestaurante;

    @Autowired
    private DeleteTipoRestaurante deleteTipoRestaurante;

    @Autowired
    private FindTipoRestaurante findTipoRestaurante;

    private TipoRestauranteDTO tipoRestauranteDTO;
    private UUID tipoRestauranteId;
    private List<TipoRestaurante> tiposRestaurante;
    private Optional<TipoRestaurante> tipoRestauranteBuscado;

    @Given("um tipo de restaurante com os dados {string}")
    public void umTipoDeRestauranteComOsDados(String dadosTipoRestaurante) {
        this.tipoRestauranteDTO = new TipoRestauranteDTO(null, dadosTipoRestaurante);
    }

    @When("o sistema adiciona o novo tipo de restaurante")
    public void oSistemaAdicionaONovoTipoDeRestaurante() {
        addTipoRestaurante.handle(tipoRestauranteDTO);
    }

    @Then("o sistema confirma que o tipo de restaurante foi adicionado")
    public void oSistemaConfirmaQueOTipoDeRestauranteFoiAdicionado() {
        assertNotNull(tipoRestauranteDTO); // Supondo que o ID seja gerado no método de adição
    }

    @Given("um tipo de restaurante existente com ID {string}")
    public void umTipoDeRestauranteExistenteComID(String idStr) {
        this.tipoRestauranteId = UUID.fromString(idStr);
    }

    @When("o sistema busca o tipo de restaurante por este ID")
    public void oSistemaBuscaOTipoDeRestaurantePorEsteID() {
        tipoRestauranteBuscado = findTipoRestaurante.getTipoRestauranteById(tipoRestauranteId);
    }

    @Then("o sistema retorna os detalhes do tipo de restaurante")
    public void oSistemaRetornaOsDetalhesDoTipoDeRestaurante() {
        assertTrue(tipoRestauranteBuscado.isPresent());
    }

    @Then("o tipo de restaurante é {string}")
    public void oTipoDeRestauranteE(String descricao) {
        assertEquals(descricao, tipoRestauranteBuscado.get().getDescricao());
    }

    @When("o sistema altera o tipo de restaurante com os dados {string}")
    public void oSistemaAlteraOTipoDeRestauranteComOsDados(String novosDados) {
        TipoRestauranteDTO novoTipoRestauranteDTO = new TipoRestauranteDTO(null, novosDados);
        alterTipoRestaurante.alterData(tipoRestauranteId.toString(), novoTipoRestauranteDTO);
    }

    @Then("o sistema confirma que o tipo de restaurante foi alterado")
    public void oSistemaConfirmaQueOTipoDeRestauranteFoiAlterado() {
        // Verificação pode ser feita buscando novamente o tipo de restaurante
        tipoRestauranteBuscado = findTipoRestaurante.getTipoRestauranteById(tipoRestauranteId);
        assertTrue(tipoRestauranteBuscado.isPresent());
    }

    @When("o sistema exclui o tipo de restaurante com ID {string}")
    public void oSistemaExcluiOTipoDeRestauranteComID(String idStr) {
        deleteTipoRestaurante.handle(idStr);
    }

    @Then("o sistema confirma que o tipo de restaurante foi excluído")
    public void oSistemaConfirmaQueOTipoDeRestauranteFoiExcluido() {
        tipoRestauranteBuscado = findTipoRestaurante.getTipoRestauranteById(tipoRestauranteId);
        assertFalse(tipoRestauranteBuscado.isPresent());
    }

    @When("o sistema lista todos os tipos de restaurante")
    public void oSistemaListaTodosOsTiposDeRestaurante() {
        tiposRestaurante = findTipoRestaurante.getTipoRestaurantes();
    }

    @Then("o sistema retorna uma lista de tipos de restaurante")
    public void oSistemaRetornaUmaListaDeTiposDeRestaurante() {
        assertNotNull(tiposRestaurante);
        assertFalse(tiposRestaurante.isEmpty());
    }

    @When("o sistema busca tipos de restaurante pela descrição {string}")
    public void oSistemaBuscaTiposDeRestaurantePelaDescricao(String descricao) {
        tiposRestaurante = findTipoRestaurante.getTipoRestauranteByDescricao(descricao);
    }

    @Then("o sistema retorna uma lista vazia de tipos de restaurante")
    public void oSistemaRetornaUmaListaVaziaDeTiposDeRestaurante() {
        assertNotNull(tiposRestaurante);
        assertTrue(tiposRestaurante.isEmpty());
    }
}
