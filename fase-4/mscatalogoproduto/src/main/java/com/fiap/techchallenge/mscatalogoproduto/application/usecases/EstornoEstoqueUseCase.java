package com.fiap.techchallenge.mscatalogoproduto.application.usecases;

import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Produto;
import com.fiap.techchallenge.mscatalogoproduto.domain.repositories.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EstornoEstoqueUseCase {

    private final ProdutoRepository repository;

    public EstornoEstoqueUseCase(ProdutoRepository repository) {
        this.repository = repository;
    }

    public Produto execute(UUID id, Integer quantidade) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID: " + id));

        produto.setQtdEstoque(produto.getQtdEstoque() + quantidade);
        return repository.save(produto);
    }

}
