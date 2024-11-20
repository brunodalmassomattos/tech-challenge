package com.fiap.techchallenge.mscatalogoproduto.application.usecases;

import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Produto;
import com.fiap.techchallenge.mscatalogoproduto.domain.repositories.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllProdutosUseCase {

    private final ProdutoRepository repository;

    public GetAllProdutosUseCase(ProdutoRepository repository) {
        this.repository = repository;
    }

    public List<Produto> execute() {
        return repository.findAll();
    }
}
