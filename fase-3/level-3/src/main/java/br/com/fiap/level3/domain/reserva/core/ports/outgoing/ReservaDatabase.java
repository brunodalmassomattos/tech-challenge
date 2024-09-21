package br.com.fiap.level3.domain.reserva.core.ports.outgoing;

import br.com.fiap.level3.domain.reserva.core.model.enums.StatusEnum;
import br.com.fiap.level3.domain.reserva.core.model.reserva.Reserva;
import br.com.fiap.level3.domain.reserva.core.model.restaurante.Restaurante;
import br.com.fiap.level3.domain.reserva.core.model.usuario.Usuario;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface ReservaDatabase {
    Reserva save(Reserva reserva);

    Optional<Reserva> getReservaAbertaByUsuarioAndData(UUID usuarioId, LocalDate data);

    Long getQuantidadeLugaresReservadosByRestaurante(UUID restauranteId);

    Optional<Usuario> getUsuarioById(UUID id);

    Optional<Restaurante> getRestauranteById(UUID id);

    List<Reserva> listarReservasPorRestaurante(UUID restauranteId);
    int getCapacidadeRestaurante(UUID restauranteId);
    Optional<Reserva> getReservaPorId(UUID reservaId);
    Optional<Reserva> atualizarStatusReserva(UUID reservaId, StatusEnum novoStatus);
}
