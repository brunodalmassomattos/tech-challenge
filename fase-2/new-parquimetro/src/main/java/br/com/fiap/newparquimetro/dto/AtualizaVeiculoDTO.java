package br.com.fiap.newparquimetro.dto;

import br.com.fiap.newparquimetro.domain.veiculo.VeiculoJava;
import jakarta.validation.constraints.NotNull;

public record AtualizaVeiculoDTO(@NotNull String id, String fabricante, String modelo, String placa, String cor, String renavam, int ano) {

    public AtualizaVeiculoDTO(VeiculoJava veiculo){
        this(veiculo.getId(), veiculo.getFabricante(), veiculo.getModelo(), veiculo.getPlaca(), veiculo.getCor(), veiculo.getRenavam(), veiculo.getAno());
    }
}
