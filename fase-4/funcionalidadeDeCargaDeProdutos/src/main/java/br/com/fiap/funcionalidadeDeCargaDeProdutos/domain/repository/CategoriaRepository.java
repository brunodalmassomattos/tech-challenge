package br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.repository;

import br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, UUID> {
    Optional<Categoria> findByDescricao(String descricao);
}