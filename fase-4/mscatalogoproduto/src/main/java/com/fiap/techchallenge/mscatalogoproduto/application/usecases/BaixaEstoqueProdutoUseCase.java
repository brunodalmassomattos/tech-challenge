package com.fiap.techchallenge.mscatalogoproduto.application.usecases;

import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Produto;
import com.fiap.techchallenge.mscatalogoproduto.domain.repositories.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BaixaEstoqueProdutoUseCase {

    private final ProdutoRepository repository;

    public BaixaEstoqueProdutoUseCase(ProdutoRepository repository) {
        this.repository = repository;
    }

    public Produto execute(UUID id, Integer quantidade) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Id do Produto não encontrado: " + id));

        if (produto.getQtdEstoque() < quantidade) {
            throw new IllegalArgumentException("Quantidade insuficiente no estoque.");
        }

        produto.setQtdEstoque(produto.getQtdEstoque() - quantidade);
        return repository.save(produto);
    }

}
