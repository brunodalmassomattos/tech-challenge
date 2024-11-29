package com.fiap.techchallenge.mscatalogoproduto.application.dtos.categoria;

import jakarta.validation.constraints.NotBlank;

public record CategoriaRequest(
        @NotBlank(message = "Descrição é obrigatória.")
        String descricao) {
}
