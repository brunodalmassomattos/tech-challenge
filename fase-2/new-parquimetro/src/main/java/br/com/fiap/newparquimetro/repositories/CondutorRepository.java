package br.com.fiap.newparquimetro.repositories;

import br.com.fiap.newparquimetro.domain.condutor.Condutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CondutorRepository extends JpaRepository<Condutor, String> {
}
