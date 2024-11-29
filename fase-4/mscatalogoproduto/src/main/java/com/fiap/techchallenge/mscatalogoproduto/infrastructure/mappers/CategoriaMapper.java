package com.fiap.techchallenge.mscatalogoproduto.infrastructure.mappers;

import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Categoria;
import com.fiap.techchallenge.mscatalogoproduto.infrastructure.persistence.entities.CategoriaEntity;

public class CategoriaMapper {

    public static Categoria toDomain(CategoriaEntity categoriaEntity) {
        return new Categoria(categoriaEntity.getId(), categoriaEntity.getDescricao());
    }

    public static CategoriaEntity toEntityDescricao(Categoria categoria) {
        return new CategoriaEntity(categoria.getDescricao());
    }

    public static CategoriaEntity toEntity(Categoria categoria) {
        return new CategoriaEntity(categoria.getId(), categoria.getDescricao());
    }

}
