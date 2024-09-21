package br.com.fiap.level3.domain.reserva.infrastructure;

import br.com.fiap.level3.domain.reserva.core.model.enums.StatusEnum;
import br.com.fiap.level3.domain.reserva.core.model.reserva.Reserva;
import br.com.fiap.level3.domain.reserva.core.model.restaurante.Restaurante;
import br.com.fiap.level3.domain.reserva.core.model.usuario.Usuario;
import br.com.fiap.level3.domain.reserva.core.ports.outgoing.ReservaDatabase;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import java.util.List;
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
    }

    @Override
    public Long getQuantidadeLugaresReservadosByRestaurante(UUID restauranteId) {
        String query = """
                SELECT sum(r.quantidadePessoas)
                FROM Reserva r
                WHERE r.restaurante.id = :restauranteId
                AND r.status IN (:reservasNaoFinalizadas)
                """;

        return entityManager.createQuery(query, Long.class)
                       .setParameter("restauranteId", restauranteId)
                       .setParameter("reservasNaoFinalizadas", StatusEnum.getStatusNaoFinalizados())
                       .getSingleResult();
    }

    @Override
    public Optional<Usuario> getUsuarioById(UUID id) {
        return Optional.ofNullable(entityManager.find(Usuario.class, id));
    }

    @Override
    public Optional<Restaurante> getRestauranteById(UUID id) {
        return Optional.ofNullable(entityManager.find(Restaurante.class, id));
    }

    @Override
    public List<Reserva> listarReservasPorRestaurante(UUID restauranteId) {
        String query = """
                SELECT r
                FROM Reserva r
                WHERE r.restaurante.id = :restauranteId
                AND r.status IN (:reservasNaoFinalizadas)
                ORDER BY r.data DESC, r.hora DESC
                """;

        return entityManager.createQuery(query, Reserva.class)
                .setParameter("restauranteId", restauranteId)
                .setParameter("reservasNaoFinalizadas", StatusEnum.getStatusNaoFinalizados())
                .getResultList();
    }

    @Override
    public int getCapacidadeRestaurante(UUID restauranteId) {
        String query = """
                SELECT r.capacidade
                FROM Restaurante r
                WHERE r.id = :restauranteId
                """;

        return entityManager.createQuery(query, Integer.class)
                .setParameter("restauranteId", restauranteId)
                .getSingleResult();
    }

    @Override
    public Optional<Reserva> getReservaPorId(UUID reservaId) {
        return Optional.ofNullable(entityManager.find(Reserva.class, reservaId));
    }

    @Override
    @Transactional
    public Optional<Reserva> atualizarStatusReserva(UUID reservaId, StatusEnum novoStatus) {
        Reserva reserva = entityManager.find(Reserva.class, reservaId);
        if (reserva != null) {
            reserva.setStatus(novoStatus.getDescricao());
            entityManager.merge(reserva);
            return Optional.of(reserva);
        }
        return Optional.empty();
    }
}
