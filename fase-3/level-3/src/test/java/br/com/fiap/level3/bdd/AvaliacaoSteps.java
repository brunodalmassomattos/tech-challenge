package br.com.fiap.level3.bdd;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import br.com.fiap.level3.domain.avaliacao.core.domain.model.avaliacao.Avaliacao;
import br.com.fiap.level3.domain.avaliacao.core.ports.incoming.AddAvaliacao;
import br.com.fiap.level3.domain.avaliacao.core.ports.incoming.AlterAvaliacao;
import br.com.fiap.level3.domain.avaliacao.core.ports.incoming.FindAvaliacao;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AvaliacaoSteps {

	private UUID avaliacaoId;

	private UUID restauranteId;

	private Optional<Avaliacao> avaliacaoEncontrada;

	private List<Optional<Avaliacao>> avaliacoesEncontradas;

	@Autowired
	@Qualifier("FindAvaliacao")
	private FindAvaliacao findAvaliacao;

	@Autowired
    @Qualifier("AlterAvaliacao")
    private AlterAvaliacao alterAvaliacao;

	@Given("um avaliacao com ID {string}")
	public void umAvaliacaoComID(String avaliacaoIdStr) {
		this.avaliacaoId = UUID.fromString(avaliacaoIdStr);
	}

	@When("o sistema retorna a avalicao por esse ID")
	public void buscaAvaliacaoComId() {
		avaliacaoEncontrada = Optional.ofNullable(findAvaliacao.getAvaliacaoById(avaliacaoId).orElse(null));
	}

	@Then("o sistema retorna detalhes da avaliacao")
	public void oSistemaRetornaOsDetalhesDaAvaliacao() {
		assertNotNull(avaliacaoEncontrada);
	}

	@Then("a nota da avaliacao é {int}")
	public void aNotaDaAvaliacaoE(Integer nota) {
		assertEquals(nota, avaliacaoEncontrada.get().getNota());
	}

	@Then("o comentario da avaliacao é {string}")
	public void oComentarioDaAvaliacaoE(String comentario) {
		assertEquals(comentario, avaliacaoEncontrada.get().getComentario());
	}

	@When("a nota da avalicao é alterada para {int}")
	public void aNotaDaAvaliacaoEAtualizadoPara(Integer novaNota) {

		avaliacaoEncontrada = findAvaliacao.getAvaliacaoById(avaliacaoId);
		avaliacaoEncontrada.get().setNota(novaNota);
		alterAvaliacao.alterAvaliacao(avaliacaoEncontrada.get());
		avaliacaoEncontrada = findAvaliacao.getAvaliacaoById(avaliacaoId);

	}

	@Then("a nota da avaliacao é atualizada com sucesso")
	public void aNotaDaAvaliacaoEAtualizadaComSucesso() {
		assertNotNull(avaliacaoEncontrada);
	}

	@Then("a nova nota da avaliacao é {int}")
	public void aNovaNotaDaAvaliacaoE(Integer nota) {
		assertEquals(nota, avaliacaoEncontrada.get().getNota());

	}
	
	@When("o comentario da avalicao é alterado para {string}")
	public void oComentarioDaAvaliacaoEAtualizadoPara(String novoComentario) {

		avaliacaoEncontrada = findAvaliacao.getAvaliacaoById(avaliacaoId);
		avaliacaoEncontrada.get().setComentario(novoComentario);
		alterAvaliacao.alterAvaliacao(avaliacaoEncontrada.get());
		avaliacaoEncontrada = findAvaliacao.getAvaliacaoById(avaliacaoId);

	}

	@Then("o comentario da avaliacao é atualizado com sucesso")
	public void aNotaDaAvaliacaoEAtualizadoComSucesso() {
		assertNotNull(avaliacaoEncontrada);
	}

	@Then("o novo comentario da avaliacao é {string}")
	public void oNovoComentarioDaAvaliacaoE(String comentario) {
		assertEquals(comentario, avaliacaoEncontrada.get().getComentario());

	}

}
