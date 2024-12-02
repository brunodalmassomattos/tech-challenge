package com.fiap.techchallenge.mscatalogoproduto.infrastructure.persistence.repositories;

import com.fiap.techchallenge.mscatalogoproduto.infrastructure.persistence.entities.CategoriaEntity;
import com.fiap.techchallenge.mscatalogoproduto.infrastructure.persistence.entities.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DataCategoriaRepository extends JpaRepository<CategoriaEntity, UUID> {
    boolean existsByDescricao(String descricao);
}
