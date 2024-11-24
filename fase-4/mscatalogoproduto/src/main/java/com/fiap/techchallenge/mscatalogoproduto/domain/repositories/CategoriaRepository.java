package com.fiap.techchallenge.mscatalogoproduto.domain.repositories;

import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Categoria;

import java.util.Optional;
import java.util.UUID;

public interface CategoriaRepository {

    Optional<Categoria> findById(UUID id);
}
