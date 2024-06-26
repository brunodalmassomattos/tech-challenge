package br.com.fiap.newparquimetro.repositories;


import br.com.fiap.newparquimetro.domain.veiculo.VeiculoJava;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeiculoRepository extends JpaRepository<VeiculoJava, String> {
}
