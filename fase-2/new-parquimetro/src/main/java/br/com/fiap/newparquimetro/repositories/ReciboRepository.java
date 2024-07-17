package br.com.fiap.newparquimetro.repositories;

import br.com.fiap.newparquimetro.domain.emissaorecibo.Recibo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReciboRepository extends JpaRepository<Recibo, String> {
    @Query("SELECT recibo " +
                   "FROM Recibo recibo " +
                   "WHERE recibo.pagamento.condutor.id = :idCondutor " +
                   "AND recibo.data BETWEEN :dataInicio AND :dataFim")
    List<Recibo> consultarPorIdCondutorEDataInicioEDataFim(@Param("idCondutor") String idCondutor,
                                                           @Param("dataInicio") LocalDateTime dataInicio,
                                                           @Param("dataFim") LocalDateTime dataFim);
}
