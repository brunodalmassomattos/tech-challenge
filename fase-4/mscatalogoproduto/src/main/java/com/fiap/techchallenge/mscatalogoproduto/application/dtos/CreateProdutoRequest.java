package com.fiap.techchallenge.mscatalogoproduto.application.dtos;

public record CreateProdutoRequest(String nome,
                                   String descricao,
                                   double preco,
                                   int quantidadeEstoque,
                                   String idCategoria) {

    public void validate() {
        if (idCategoria == null || idCategoria.isBlank()) throw new IllegalArgumentException("O Id da Categoria não pode estar em branco.");
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome não pode estar em branco.");
        if (descricao == null || descricao.isBlank()) throw new IllegalArgumentException("Descrição não pode estar em branco.");
        if (preco < 0) throw new IllegalArgumentException("Preço não pode ser negativo ou estar zerado.");
        if (quantidadeEstoque < 0) throw new IllegalArgumentException("Quatidade Estoque não pode ser negativo");
    }
}
