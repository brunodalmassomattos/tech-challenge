package com.fiap.techchallenge.mscatalogoproduto.application.dtos.categoria;

import jakarta.validation.constraints.NotBlank;

public record CategoriaResponse(
        String id,
        String descricao) {
}
