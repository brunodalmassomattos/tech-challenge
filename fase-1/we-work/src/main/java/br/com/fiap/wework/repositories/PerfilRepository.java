package br.com.fiap.wework.repositories;

import br.com.fiap.wework.domain.usuario.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PerfilRepository extends JpaRepository<Perfil, String> {
    Optional<Perfil> findByDescricao(String descricao);
}
