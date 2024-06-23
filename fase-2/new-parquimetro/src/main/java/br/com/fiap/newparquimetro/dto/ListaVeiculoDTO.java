package br.com.fiap.newparquimetro.dto;

import br.com.fiap.newparquimetro.domain.veiculo.VeiculoJava;

public record ListaVeiculoDTO(Long id, String fabricante, String modelo, String placa, String cor, String renavam, int ano) {

    public ListaVeiculoDTO(VeiculoJava veiculo){
        this(veiculo.getId(), veiculo.getFabricante(), veiculo.getModelo(), veiculo.getPlaca(), veiculo.getCor(), veiculo.getRenavam(), veiculo.getAno());
    }
}
