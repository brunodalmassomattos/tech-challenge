package br.com.fiap.newparquimetro.dto;

import br.com.fiap.newparquimetro.domain.veiculo.VeiculoJava;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public record AtualizaVeiculoDTO(
        Optional<String> fabricante,
        Optional<String> modelo,
        Optional<String> placa,
        Optional<String> cor,
        Optional<Integer> ano) {

    public AtualizaVeiculoDTO(VeiculoJava veiculo) {
        this(
                Optional.ofNullable(veiculo.getFabricante()),
                Optional.ofNullable(veiculo.getModelo()),
                Optional.ofNullable(veiculo.getPlaca()),
                Optional.ofNullable(veiculo.getCor()),
                Optional.ofNullable(veiculo.getAno())
        );
    }
}
