package br.com.fiap.newparquimetro.repositories;

import br.com.fiap.newparquimetro.domain.emissaorecibo.Recibo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReciboRepository extends JpaRepository<Recibo, UUID> {

    @Query("SELECT recibo FROM Recibo AS recibo WHERE recibo.condutor.id = ?1")
    Optional<Recibo> findByCondutorId(String condutorId);
}
