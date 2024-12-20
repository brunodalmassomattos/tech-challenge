package com.fiap.techchallenge.mscatalogoproduto.application.dtos.produto;

import java.math.BigDecimal;

public record ProdutoResponse(
        String id,
        String nome,
        String descricao,
        BigDecimal preco,
        int qtdEstoque,
        CategoriaResponse categoriaResponse) {
}
