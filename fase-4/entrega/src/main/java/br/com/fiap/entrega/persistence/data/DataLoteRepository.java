package br.com.fiap.entrega.persistence.data;

import br.com.fiap.entrega.domain.entity.Lote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface DataLoteRepository extends JpaRepository<Lote, UUID> {

    @Query("SELECT lote " +
                   "FROM Lote lote " +
                   "WHERE lote.cepEntrega LIKE :cep% " +
                   "AND lote.status = :situacao")
    Optional<Lote> findByCep(@Param("cep") String cep, @Param("situacao") String situacao);
}
