package br.com.fiap.newparquimetro.repositories;

import br.com.fiap.newparquimetro.domain.usuario.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PerfilRepository extends JpaRepository<Perfil, String> {
    Optional<Perfil> findByDescricao(String descricao);
}
