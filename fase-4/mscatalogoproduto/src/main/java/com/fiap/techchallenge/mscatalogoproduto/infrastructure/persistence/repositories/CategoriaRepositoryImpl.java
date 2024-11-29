package com.fiap.techchallenge.mscatalogoproduto.infrastructure.persistence.repositories;

import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Categoria;
import com.fiap.techchallenge.mscatalogoproduto.domain.repositories.CategoriaRepository;
import com.fiap.techchallenge.mscatalogoproduto.infrastructure.mappers.CategoriaMapper;
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

    @Override
    public List<Categoria> findAll() {
        return repository.findAll().stream().map(CategoriaMapper::toDomain).toList();
    }

    @Override
    public Categoria save(Categoria categoriaRequest) {
        var categoria = repository.save(CategoriaMapper.toEntity(categoriaRequest));
        return CategoriaMapper.toDomain(categoria);
    }

    @Override
    public boolean existsByDescricao(String descricao) {
        return repository.existsByDescricao(descricao);
    }

    @Override
    public void delete(Categoria categoria) {
        repository.delete(CategoriaMapper.toEntity(categoria));
    }

}
