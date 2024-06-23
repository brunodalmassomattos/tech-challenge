package br.com.fiap.newparquimetro.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CadastraVeiculoDTO(
    @NotBlank
    String fabricante,
    @NotBlank
    String modelo,
    @NotBlank
    String placa,
    @NotBlank
    String cor,
    @NotBlank
    String renavam,
    @NotNull
    int ano){
}
