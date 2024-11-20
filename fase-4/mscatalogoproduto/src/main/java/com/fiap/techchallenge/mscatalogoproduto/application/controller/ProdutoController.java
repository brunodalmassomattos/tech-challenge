package com.fiap.techchallenge.mscatalogoproduto.application.controller;

import com.fiap.techchallenge.mscatalogoproduto.application.dtos.CategoriaResponse;
import com.fiap.techchallenge.mscatalogoproduto.application.dtos.ProdutoResponse;
import com.fiap.techchallenge.mscatalogoproduto.application.usecases.GetAllProdutosUseCase;
import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Produto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final GetAllProdutosUseCase getAllProdutosUseCase;

    public ProdutoController(GetAllProdutosUseCase getAllProdutosUseCase) {
        this.getAllProdutosUseCase = getAllProdutosUseCase;
    }

    @GetMapping
    public List<ProdutoResponse> getProduct() {
        List<Produto> produtos = getAllProdutosUseCase.execute();

        return produtos.stream()
                .map(p -> new ProdutoResponse(
                        p.getId().toString(),
                        p.getNome(),
                        p.getDescricao(),
                        p.getPreco(),
                        p.getQtdEstoque(),
                        new CategoriaResponse(
                                p.getCategoria().getId().toString(),
                                p.getCategoria().getDescricao())
                ))
                .toList();
    }
}
