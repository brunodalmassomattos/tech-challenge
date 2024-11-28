package br.com.fiap.funcionalidadeDeCargaDeProdutos.application.dto;

import jakarta.validation.constraints.*;
import java.util.UUID;

public record CategoriaDTO(
        UUID id,

        @NotBlank(message = "A descrição da categoria é obrigatória.")
        @Size(min = 2, max = 100, message = "A descrição deve ter entre 2 e 100 caracteres.")
        String descricao
) {}
