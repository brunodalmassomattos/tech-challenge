package com.fiap.techchallenge.mscatalogoproduto.application.usecases.categoria;

import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Categoria;
import com.fiap.techchallenge.mscatalogoproduto.domain.repositories.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteCategoriaUseCase {

    private final CategoriaRepository repository;

    public DeleteCategoriaUseCase(CategoriaRepository repository) {
        this.repository = repository;
    }

    public void execute(UUID id) {
        Categoria categoria = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrado com ID: " + id));

        repository.delete(categoria);
    }
}
