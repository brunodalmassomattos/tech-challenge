package br.com.fiap.newparquimetro.repositories;


import br.com.fiap.newparquimetro.domain.veiculo.VeiculoJava;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VeiculoRepository extends JpaRepository<VeiculoJava, String> {
    Optional<VeiculoJava> findByPlaca(String placa);
}
