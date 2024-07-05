package br.com.fiap.newparquimetro.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import br.com.fiap.newparquimetro.domain.condutor.Tempo;

@Repository
public interface ControleTempoRepository extends JpaRepository<Tempo, String>{

	@Query("SELECT t FROM Tempo t WHERE t.idCondutor = :idCondutor AND t.status = :status")
	List<Tempo> findTempoAtivoCondutorStatus(@Param("idCondutor") String idCondutor,@Param("status") String status);
	
	@Query("SELECT t FROM Tempo t WHERE t.data between :dataIni and :dataFim and t.status = :status")
	List<Tempo> findTempoAtivoDataStatus(@Param("dataIni") Date dataIni, @Param("dataFim") Date dataFim,@Param("status") String status);

}

