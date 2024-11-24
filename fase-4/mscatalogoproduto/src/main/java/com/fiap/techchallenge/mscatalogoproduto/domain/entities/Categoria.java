package com.fiap.techchallenge.mscatalogoproduto.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {

    private UUID id;
    private String descricao;

    public Categoria(UUID id) {
        this.id = id;
    }
}


