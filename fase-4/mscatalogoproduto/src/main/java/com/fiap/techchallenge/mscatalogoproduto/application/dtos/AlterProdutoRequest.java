package com.fiap.techchallenge.mscatalogoproduto.application.dtos;

import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Categoria;
import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Produto;

import java.math.BigDecimal;
import java.util.UUID;

public record AlterProdutoRequest(
        String nome,
        String descricao,
        double preco,
        int quantidadeEstoque,
        String idCategoria) {

    public static Produto buildProdutoResquest(UUID idProduto, AlterProdutoRequest request) {
        return new Produto(
                idProduto,
                request.nome,
                request.descricao,
                new BigDecimal(request.preco),
                request.quantidadeEstoque,
                request.idCategoria == null || request.idCategoria.isEmpty()
                        ? new Categoria()
                        : new Categoria(UUID.fromString(request.idCategoria)));
    }

    public void validate() {
        if (idCategoria == null || idCategoria.isBlank()) throw new IllegalArgumentException("O Id da Categoria não pode estar em branco.");
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome não pode estar em branco.");
        if (descricao == null || descricao.isBlank()) throw new IllegalArgumentException("Descrição não pode estar em branco.");
        if (preco < 0) throw new IllegalArgumentException("Preço não pode ser negativo ou estar zerado.");
        if (quantidadeEstoque < 0) throw new IllegalArgumentException("Quatidade Estoque não pode ser negativo");
    }
}
