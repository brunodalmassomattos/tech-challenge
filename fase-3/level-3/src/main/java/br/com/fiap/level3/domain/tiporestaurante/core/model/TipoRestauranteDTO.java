package br.com.fiap.level3.domain.tiporestaurante.core.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TipoRestauranteDTO(
        @NotNull(message = "O campo descrição é obrigatorio")
        @NotBlank(message = "O campo descrição é obrigatorio")
        String descricao) {
}
