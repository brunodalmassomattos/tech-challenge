package br.com.fiap.newparquimetro.repositories;

import br.com.fiap.newparquimetro.domain.condutor.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, String> {
}
