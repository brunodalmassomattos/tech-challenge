package br.com.fiap.funcionalidadeDeCargaDeProdutos.application.dto;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class ProdutoDTO {

    private Long id;

    @NotBlank(message = "O nome do produto é obrigatório.")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres.")
    private String nome;

    @Size(max = 255, message = "A descrição não pode exceder 255 caracteres.")
    private String descricao;

    @NotNull(message = "O preço é obrigatório.")
    @DecimalMin(value = "0.0", inclusive = false, message = "O preço deve ser maior que zero.")
    private Double preco;

    @NotNull(message = "A quantidade em estoque é obrigatória.")
    @Min(value = 0, message = "A quantidade em estoque não pode ser negativa.")
    private Integer qtdEstoque;

    @NotNull(message = "A categoria é obrigatória.")
    private Long categoriaId;
}

