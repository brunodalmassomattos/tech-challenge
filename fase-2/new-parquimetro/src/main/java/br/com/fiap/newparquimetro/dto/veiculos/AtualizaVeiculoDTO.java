package br.com.fiap.newparquimetro.dto.veiculos;

import br.com.fiap.newparquimetro.domain.veiculo.VeiculoJava;

import java.util.Optional;

public record AtualizaVeiculoDTO(
        Optional<String> fabricante,
        Optional<String> modelo,
        Optional<String> placa,
        Optional<String> cor,
        Optional<String> ano) {

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
