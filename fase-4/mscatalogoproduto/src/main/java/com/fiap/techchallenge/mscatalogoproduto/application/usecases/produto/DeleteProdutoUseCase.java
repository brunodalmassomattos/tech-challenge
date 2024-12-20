package com.fiap.techchallenge.mscatalogoproduto.application.usecases.produto;

import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Produto;
import com.fiap.techchallenge.mscatalogoproduto.domain.repositories.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteProdutoUseCase {

    private final ProdutoRepository repository;

    public DeleteProdutoUseCase(ProdutoRepository repository) {
        this.repository = repository;
    }

    public void execute(UUID id) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID: " + id));

        repository.delete(produto);
    }
}
