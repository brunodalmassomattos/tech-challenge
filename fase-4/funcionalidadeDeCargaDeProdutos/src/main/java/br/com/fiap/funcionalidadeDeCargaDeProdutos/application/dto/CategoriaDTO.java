package br.com.fiap.funcionalidadeDeCargaDeProdutos.application.dto;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class CategoriaDTO {

    private Long id;

    @NotBlank(message = "A descrição da categoria é obrigatória.")
    @Size(min = 2, max = 100, message = "A descrição deve ter entre 2 e 100 caracteres.")
    private String descricao;
}

