package com.fiap.techchallenge.mscatalogoproduto.infrastructure.persistence.repositories;

import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Categoria;
import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Produto;
import com.fiap.techchallenge.mscatalogoproduto.domain.repositories.CategoriaRepository;
import com.fiap.techchallenge.mscatalogoproduto.domain.repositories.ProdutoRepository;
import com.fiap.techchallenge.mscatalogoproduto.infrastructure.mappers.CategoriaMapper;
import com.fiap.techchallenge.mscatalogoproduto.infrastructure.mappers.ProdutoMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CategoriaRepositoryImpl implements CategoriaRepository {

    private final DataCategoriaRepository repository;

    public CategoriaRepositoryImpl(DataCategoriaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Categoria> findById(UUID id) {
        return repository.findById(id).map(CategoriaMapper::toDomain);
    }

}
