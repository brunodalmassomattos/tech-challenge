package com.fiap.techchallenge.mscatalogoproduto.infrastructure.mappers;

import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Produto;
import com.fiap.techchallenge.mscatalogoproduto.infrastructure.persistence.entities.ProdutoEntity;

public class ProdutoMapper {
    public static ProdutoEntity toEntity(Produto produto) {
        ProdutoEntity entity = new ProdutoEntity();
        entity.setId(produto.getId());
        entity.setNome(produto.getNome());
        entity.setDescricao(produto.getDescricao());
        entity.setPreco(produto.getPreco());
        entity.setQtdEstoque(produto.getQtdEstoque());
        return entity;
    }

    public static Produto toDomain(ProdutoEntity entity) {
        return new Produto(entity);
    }
}
