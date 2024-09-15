package br.com.fiap.level3.domain.reserva.core.ports.incoming;

import br.com.fiap.level3.domain.reserva.core.model.reserva.ReservaDTO;

public interface CreateNewReserva {
    ReservaDTO createNewReserva(ReservaDTO reserva);
}
