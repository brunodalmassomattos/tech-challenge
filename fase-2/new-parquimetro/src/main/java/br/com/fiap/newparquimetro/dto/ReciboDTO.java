package br.com.fiap.newparquimetro.dto;

import lombok.Builder;

import java.time.LocalTime;

@Builder
public record ReciboDTO(
        LocalTime tempo,
        Double valorTotal,
        String nomeCondutor,
        String cpfCnpjCondutor,
        TarifaDTO tarifa
) {
}
