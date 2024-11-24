package com.fiap.techchallenge.mscatalogoproduto.infrastructure.mappers;

import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Categoria;
import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Produto;
import com.fiap.techchallenge.mscatalogoproduto.infrastructure.persistence.entities.CategoriaEntity;
import com.fiap.techchallenge.mscatalogoproduto.infrastructure.persistence.entities.ProdutoEntity;

public class ProdutoMapper {
    public static ProdutoEntity toEntity(Produto produto) {
        ProdutoEntity entity = new ProdutoEntity();
        entity.setId(produto.getId());
        entity.setNome(produto.getNome().toLowerCase());
        entity.setDescricao(produto.getDescricao().toLowerCase());
        entity.setPreco(produto.getPreco());
        entity.setQtdEstoque(produto.getQtdEstoque());
        entity.setCategoriaEntity(buildCategoria(produto.getCategoria()));
        return entity;
    }

    private static CategoriaEntity buildCategoria(Categoria categoria) {
        return new CategoriaEntity(categoria.getId(), categoria.getDescricao().toLowerCase());
    }

    public static Produto toDomain(ProdutoEntity entity) {
        return new Produto(entity);
    }
}
