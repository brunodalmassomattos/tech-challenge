package br.com.fiap.newparquimetro.repositories;

import br.com.fiap.newparquimetro.domain.condutor.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, String> {
}
