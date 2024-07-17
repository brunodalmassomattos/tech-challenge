package br.com.fiap.newparquimetro.dto.veiculos;

import br.com.fiap.newparquimetro.domain.veiculo.VeiculoJava;

public record ListaVeiculoDTO(String id, String fabricante, String modelo, String placa, String cor, String ano) {

    public ListaVeiculoDTO(VeiculoJava veiculo){
        this(veiculo.getId(), veiculo.getFabricante(), veiculo.getModelo(), veiculo.getPlaca(), veiculo.getCor(), veiculo.getAno());
    }
}
