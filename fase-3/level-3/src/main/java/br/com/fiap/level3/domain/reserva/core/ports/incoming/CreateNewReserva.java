package br.com.fiap.level3.domain.reserva.core.ports.incoming;

import br.com.fiap.level3.domain.reserva.core.model.enums.StatusEnum;
import br.com.fiap.level3.domain.reserva.core.model.reserva.ReservaDTO;
import br.com.fiap.level3.domain.reserva.core.model.reserva.ReservaRestauranteDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CreateNewReserva {
    ReservaDTO createNewReserva(ReservaDTO reserva);
    ReservaRestauranteDTO listarReservasPorRestaurante(UUID restauranteId);
    Optional<ReservaDTO> listarReservaPorId(UUID reservaId);
    Optional<ReservaDTO> atualizarStatusReserva(UUID reservaId, StatusEnum novoStatus);
}

