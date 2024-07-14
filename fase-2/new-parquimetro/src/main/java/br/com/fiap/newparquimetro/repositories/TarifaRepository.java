package br.com.fiap.newparquimetro.repositories;

import br.com.fiap.newparquimetro.domain.emissaorecibo.Tarifa;
import br.com.fiap.newparquimetro.dto.enums.TipoTarifaEnum;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TarifaRepository extends JpaRepository<Tarifa, String> {
	
	@Query("SELECT t.id FROM Tarifa t WHERE t.tipo = :tipo")
	String findIdTipoTarifa(@Param("tipo") Optional<TipoTarifaEnum> optional);
	
	@Query("SELECT t.tipo FROM Tarifa t WHERE t.id = :id")
	String findTipoById(@Param("id") String id);
	
}
