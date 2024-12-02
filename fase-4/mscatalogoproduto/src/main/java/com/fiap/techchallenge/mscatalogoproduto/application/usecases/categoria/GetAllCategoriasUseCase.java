package com.fiap.techchallenge.mscatalogoproduto.application.usecases.categoria;

import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Categoria;
import com.fiap.techchallenge.mscatalogoproduto.domain.repositories.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllCategoriasUseCase {

    private final CategoriaRepository repository;

    public GetAllCategoriasUseCase(CategoriaRepository repository) {
        this.repository = repository;
    }

    public List<Categoria> execute() {
        return repository.findAll();
    }
}
