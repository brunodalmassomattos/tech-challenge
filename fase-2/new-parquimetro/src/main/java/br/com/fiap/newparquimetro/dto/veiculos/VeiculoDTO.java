package br.com.fiap.newparquimetro.dto.veiculos;

import br.com.fiap.newparquimetro.domain.veiculo.VeiculoJava;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.stream.Collectors;

public record VeiculoDTO(
        String fabricante,
        @NotBlank(message = "O modelo é obrigatorio.")
        String modelo,
        @NotBlank(message = "A placa é obrigatorio.")
        String placa,
        String cor,
        String ano) {

    public static List<VeiculoJava> toVeiculos(List<VeiculoDTO> veiculos) {
        return veiculos.stream().map(dto -> new ObjectMapper().convertValue(dto, VeiculoJava.class)).collect(Collectors.toList());
    }
}
