package com.fiap.techchallenge.mscatalogoproduto.application.usecases.produto;

import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Produto;
import com.fiap.techchallenge.mscatalogoproduto.domain.repositories.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchProdutosUseCase {

    private final ProdutoRepository repository;

    public SearchProdutosUseCase(ProdutoRepository repository) {
        this.repository = repository;
    }

    public List<Produto> execute(String nome, String descricao) {
        if (nome != null && !nome.isEmpty()) {
            nome = nome.toLowerCase();
        }

        if (descricao != null && !descricao.isEmpty()) {
            descricao = descricao.toLowerCase();
        }

        return repository.findByNameOrDescription(nome, descricao);
    }
}
