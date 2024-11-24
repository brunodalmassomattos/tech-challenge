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
    public List<Produto> findByNameOrDescription(String nome, String descricao) {
        return repository.findByNomeContainingOrDescricaoContainingIgnoreCase(nome, descricao)
                .stream()
                .map(ProdutoMapper::toDomain)
                .toList();
    }

    @Override
    public Produto save(Produto produto) {
        var produtoEntity = repository.save(ProdutoMapper.toEntity(produto));
        return ProdutoMapper.toDomain(produtoEntity);
    }

    @Override
    public void delete(Produto produto) {
        repository.delete(ProdutoMapper.toEntity(produto));
    }
}
