package br.com.fiap.newparquimetro.dto.veiculos;

import java.util.Optional;

public record AtualizaVeiculoDTO(
        Optional<String> fabricante,
        Optional<String> modelo,
        Optional<String> placa,
        Optional<String> cor,
        Optional<String> ano) {
}
