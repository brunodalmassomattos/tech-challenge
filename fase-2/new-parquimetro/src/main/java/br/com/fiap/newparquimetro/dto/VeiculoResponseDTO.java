package br.com.fiap.newparquimetro.dto;

import br.com.fiap.newparquimetro.domain.veiculo.VeiculoJava;

public record VeiculoResponseDTO(String id, String fabricante, String modelo, String placa, String cor, int ano) {
    public VeiculoResponseDTO(VeiculoJava veiculo) {
        this(veiculo.getId(), veiculo.getFabricante(), veiculo.getModelo(), veiculo.getPlaca(), veiculo.getCor(), veiculo.getAno());
    }
}
