package com.fiap.techchallenge.mscatalogoproduto.infrastructure.persistence.repositories;

import com.fiap.techchallenge.mscatalogoproduto.infrastructure.persistence.entities.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DataProdutoRepository extends JpaRepository<ProdutoEntity, UUID> {
}
