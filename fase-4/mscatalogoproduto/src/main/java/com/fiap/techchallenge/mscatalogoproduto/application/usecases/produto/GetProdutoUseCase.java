package com.fiap.techchallenge.mscatalogoproduto.application.usecases.produto;

import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Produto;
import com.fiap.techchallenge.mscatalogoproduto.domain.repositories.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetProdutoUseCase {

    private final ProdutoRepository repository;

    public GetProdutoUseCase(ProdutoRepository repository) {
        this.repository = repository;
    }

    public Produto execute(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não localizado para o id: " + id));
    }
}
