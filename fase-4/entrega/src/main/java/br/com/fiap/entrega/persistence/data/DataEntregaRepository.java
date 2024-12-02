package br.com.fiap.entrega.persistence.data;

import br.com.fiap.entrega.domain.entity.Entrega;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DataEntregaRepository extends JpaRepository<Entrega, UUID> {
    Optional<Entrega> findByCodigoRastreio(String codigoRastreio);
}
