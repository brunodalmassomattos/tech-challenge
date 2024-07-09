package br.com.fiap.newparquimetro.repositories;

import br.com.fiap.newparquimetro.domain.emissaorecibo.Recibo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReciboRepository extends JpaRepository<Recibo, String> {
    List<Recibo> findByIdCondutor(String condutorId);
}
