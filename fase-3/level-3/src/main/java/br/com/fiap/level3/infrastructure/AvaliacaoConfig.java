package br.com.fiap.level3.infrastructure;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import br.com.fiap.level3.domain.avaliacao.core.AvaliacaoFacade;
import br.com.fiap.level3.domain.avaliacao.infrastructure.AvaliacaoDatabaseAdapter;
import br.com.fiap.level3.domain.avaliacao.core.ports.incoming.AddAvaliacao;
import br.com.fiap.level3.domain.avaliacao.core.ports.incoming.AlterAvaliacao;
import br.com.fiap.level3.domain.avaliacao.core.ports.incoming.FindAvaliacao;
import br.com.fiap.level3.domain.avaliacao.core.ports.outcoming.AvaliacaoDatabase;

public class AvaliacaoConfig {

    @Bean
    public AvaliacaoDatabase avaliacaoDatabase(JdbcTemplate jdbcTemplate) {
        return new AvaliacaoDatabaseAdapter(jdbcTemplate);
    }

    @Bean
    @Qualifier("FindAvaliacao")
    public FindAvaliacao findAvaliacao(AvaliacaoDatabase database) {
        return new AvaliacaoFacade(database);
    }

    @Bean
    @Qualifier("AddAvaliacao")
    public AddAvaliacao addAvaliacao(AvaliacaoDatabase database) {
        return new AvaliacaoFacade(database);
    }

    @Bean
    @Qualifier("AlterAvaliacao")
    public AlterAvaliacao alterAvaliacao(AvaliacaoDatabase database) {
        return new AvaliacaoFacade(database);
    }
	
}
