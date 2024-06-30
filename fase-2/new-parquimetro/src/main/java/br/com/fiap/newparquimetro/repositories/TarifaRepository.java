package br.com.fiap.newparquimetro.repositories;

import br.com.fiap.newparquimetro.domain.emissaorecibo.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TarifaRepository extends JpaRepository<Tarifa, UUID> {
}
