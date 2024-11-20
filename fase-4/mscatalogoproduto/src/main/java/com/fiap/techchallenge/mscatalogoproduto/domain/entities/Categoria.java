package com.fiap.techchallenge.mscatalogoproduto.domain.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class Categoria {

    private UUID id;
    private String descricao;

    public Categoria(UUID id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }
}


