package com.fiap.techchallenge.mscatalogoproduto.infrastructure.persistence.repositories;

import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Produto;
import com.fiap.techchallenge.mscatalogoproduto.domain.repositories.ProdutoRepository;
import com.fiap.techchallenge.mscatalogoproduto.infrastructure.mappers.ProdutoMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepository {

    private final DataProdutoRepository repository;

    public ProdutoRepositoryImpl(DataProdutoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Produto> findById(UUID id) {
        return repository.findById(id).map(ProdutoMapper::toDomain);
    }

    @Override
    public List<Produto> findAll() {
        return repository.findAll().stream().map(ProdutoMapper::toDomain).toList();
    }

    @Override
    public void save(Produto product) {
        repository.save(ProdutoMapper.toEntity(product));
    }
}
