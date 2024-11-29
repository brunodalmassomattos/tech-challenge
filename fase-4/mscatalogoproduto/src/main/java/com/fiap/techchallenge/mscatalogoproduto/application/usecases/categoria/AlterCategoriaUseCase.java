package com.fiap.techchallenge.mscatalogoproduto.application.usecases.categoria;

import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Categoria;
import com.fiap.techchallenge.mscatalogoproduto.domain.repositories.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AlterCategoriaUseCase {

    private final CategoriaRepository repository;

    public AlterCategoriaUseCase(CategoriaRepository repository) {
        this.repository = repository;
    }

    public Categoria execute(Categoria categoria) {
        if (repository.existsByDescricao(categoria.getDescricao())) {
            throw new EntityNotFoundException("Já existe uma categoria com esta descrição.");
        }

        var categoriaSalva = repository.findById(categoria.getId())
                .orElseThrow(() -> new RuntimeException("Categoria  não localizado para o id: " + categoria.getId()));

        return repository.save(buildCategoria(categoria, categoriaSalva));
    }

    private Categoria buildCategoria(Categoria categoria, Categoria categoriaSalva) {
        return new Categoria(
                categoriaSalva.getId(),
                categoria.getDescricao() == null || categoria.getDescricao().isEmpty()
                        ? categoriaSalva.getDescricao()
                        : categoria.getDescricao()
        );
    }
}
