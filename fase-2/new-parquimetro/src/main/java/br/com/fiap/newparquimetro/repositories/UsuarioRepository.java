package br.com.fiap.newparquimetro.repositories;

import br.com.fiap.newparquimetro.domain.condutor.Condutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Condutor, String> {
}
