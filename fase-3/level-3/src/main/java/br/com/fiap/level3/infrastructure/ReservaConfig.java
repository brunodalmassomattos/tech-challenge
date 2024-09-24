package br.com.fiap.level3.infrastructure;

import br.com.fiap.level3.domain.reserva.core.ReservaFacade;
import br.com.fiap.level3.domain.reserva.core.ports.incoming.CreateNewReserva;
import br.com.fiap.level3.domain.reserva.core.ports.outgoing.ReservaDatabase;
import br.com.fiap.level3.domain.reserva.infrastructure.ReservaDatabaseAdapter;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

public class ReservaConfig {

    @Bean
    public ReservaDatabase reservaDatabase(EntityManager entityManager) {
        return new ReservaDatabaseAdapter(entityManager);
    }

    @Bean
    @Qualifier("criarNovaReserva")
    public CreateNewReserva criarNovaReserva(ReservaDatabase reservaDatabase) {
        return new ReservaFacade(reservaDatabase);
    }
}
