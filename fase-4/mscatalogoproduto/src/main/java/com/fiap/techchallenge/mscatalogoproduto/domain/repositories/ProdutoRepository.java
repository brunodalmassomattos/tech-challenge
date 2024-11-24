package com.fiap.techchallenge.mscatalogoproduto.domain.repositories;

import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Produto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProdutoRepository {

    Optional<Produto> findById(UUID id);
    List<Produto> findAll();
    List<Produto> findByNameOrDescription(String name, String description);

    Produto save(Produto product);
    void delete(Produto product);
}
