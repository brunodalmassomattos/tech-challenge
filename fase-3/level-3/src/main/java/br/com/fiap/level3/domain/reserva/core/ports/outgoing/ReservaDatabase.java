package br.com.fiap.level3.domain.reserva.core.ports.outgoing;

import br.com.fiap.level3.domain.reserva.core.model.reserva.Reserva;
import br.com.fiap.level3.domain.reserva.core.model.restaurante.Restaurante;
import br.com.fiap.level3.domain.reserva.core.model.usuario.Usuario;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface ReservaDatabase {
    Reserva save(Reserva reserva);

    Optional<Reserva> getReservaAbertaByUsuarioAndData(UUID usuarioId, LocalDate data);

    Optional<Long> getQuantidadeLugaresReservadosByRestaurante(UUID restauranteId);

    Optional<Usuario> getUsuarioById(UUID id);

    Optional<Restaurante> getRestauranteById(UUID id);
}
