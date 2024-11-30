package br.com.fiap.entrega.persistence.data;

import br.com.fiap.entrega.domain.entity.Entrega;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DataEntregaRepository extends JpaRepository<Entrega, UUID> {

    List<Entrega> findByLoteId(UUID id);

    Optional<Entrega> findByCodigoRastreio(String codigoRastreio);


}
