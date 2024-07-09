package br.com.fiap.newparquimetro.dto.veiculos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CadastraVeiculoDTO(
    @NotBlank(message = "Fabricante é obrigatório!")
    String fabricante,
    @NotBlank(message = "Modelo é obrigatório!")
    String modelo,
    @NotBlank(message = "Placa é obrigatório!")
    String placa,
    @NotBlank(message = "Cor é obrigatório!")
    String cor,
    @NotNull(message = "Ano é obrigatório!")
    int ano){
}
