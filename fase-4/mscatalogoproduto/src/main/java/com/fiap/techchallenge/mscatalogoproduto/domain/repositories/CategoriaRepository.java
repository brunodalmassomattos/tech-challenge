package com.fiap.techchallenge.mscatalogoproduto.domain.repositories;

import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Categoria;
import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Produto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoriaRepository {
    Optional<Categoria> findById(UUID id);

    List<Categoria> findAll();

    Categoria save(Categoria categoria);

    boolean existsByDescricao(String descricao);

    void delete(Categoria categoria);
}
