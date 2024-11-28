package br.com.fiap.funcionalidadeDeCargaDeProdutos.application.dto;

import jakarta.validation.constraints.*;
import java.util.UUID;

public record ProdutoDTO(
        UUID id,

        @NotBlank(message = "O nome do produto é obrigatório.")
        @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres.")
        String nome,

        @Size(max = 255, message = "A descrição não pode exceder 255 caracteres.")
        String descricao,

        @NotNull(message = "O preço é obrigatório.")
        @DecimalMin(value = "0.0", inclusive = false, message = "O preço deve ser maior que zero.")
        Double preco,

        @NotNull(message = "A quantidade em estoque é obrigatória.")
        @Min(value = 0, message = "A quantidade em estoque não pode ser negativa.")
        Integer qtdEstoque,

        @NotNull(message = "A categoria é obrigatória.")
        UUID categoriaId
) {}