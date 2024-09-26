package br.com.fiap.level3.domain.reserva.infrastructure;

import br.com.fiap.level3.domain.reserva.core.model.enums.StatusEnum;
import br.com.fiap.level3.domain.reserva.core.model.reserva.Reserva;
import br.com.fiap.level3.domain.reserva.core.model.restaurante.Restaurante;
import br.com.fiap.level3.domain.reserva.core.model.usuario.Usuario;
import br.com.fiap.level3.domain.reserva.core.ports.outgoing.ReservaDatabase;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class ReservaDatabaseAdapter implements ReservaDatabase {

    private final EntityManager entityManager;

    @Override
    @Transactional
    public Reserva save(Reserva reserva) {
        entityManager.persist(reserva);
        return reserva;
    }

    @Override
    public Optional<Reserva> getReservaAbertaByUsuarioAndData(UUID usuarioId, LocalDate data) {
        try {
            String query = """
                SELECT r
                FROM Reserva r
                WHERE r.usuario.id = :usuarioId
                AND r.data = :data
                AND r.status IN (:reservasNaoFinalizadas)
                """;

            return Optional.ofNullable(entityManager.createQuery(query, Reserva.class)
                                               .setParameter("usuarioId", usuarioId)
                                               .setParameter("data", data)
                                               .setParameter("reservasNaoFinalizadas", StatusEnum.getStatusNaoFinalizados())
                                               .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Long> getQuantidadeLugaresReservadosByRestaurante(UUID restauranteId) {
        String query = """
                SELECT sum(r.quantidadePessoas)
                FROM Reserva r
                WHERE r.restaurante.id = :restauranteId
                AND r.status IN (:reservasNaoFinalizadas)
                """;

        return Optional.ofNullable(entityManager.createQuery(query, Long.class)
                       .setParameter("restauranteId", restauranteId)
                       .setParameter("reservasNaoFinalizadas", StatusEnum.getStatusNaoFinalizados())
                       .getSingleResult());
    }

    @Override
    public Optional<Usuario> getUsuarioById(UUID id) {
        return Optional.ofNullable(entityManager.find(Usuario.class, id));
    }

    @Override
    public Optional<Restaurante> getRestauranteById(UUID id) {
        return Optional.ofNullable(entityManager.find(Restaurante.class, id));
    }
}
