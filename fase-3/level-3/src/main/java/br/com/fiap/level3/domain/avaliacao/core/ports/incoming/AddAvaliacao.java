package br.com.fiap.level3.domain.avaliacao.core.ports.incoming;

import br.com.fiap.level3.domain.avaliacao.core.domain.model.avaliacao.Avaliacao;

public interface AddAvaliacao {
	void save(Avaliacao avaliacao);
}
