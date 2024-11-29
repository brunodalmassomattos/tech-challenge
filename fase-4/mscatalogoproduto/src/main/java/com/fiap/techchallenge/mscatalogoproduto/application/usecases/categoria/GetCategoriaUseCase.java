package com.fiap.techchallenge.mscatalogoproduto.application.usecases.categoria;

import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Categoria;
import com.fiap.techchallenge.mscatalogoproduto.domain.repositories.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetCategoriaUseCase {

    private final CategoriaRepository repository;

    public GetCategoriaUseCase(CategoriaRepository repository) {
        this.repository = repository;
    }

    public Categoria execute(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria  não localizado para o id: " + id));
    }
}
