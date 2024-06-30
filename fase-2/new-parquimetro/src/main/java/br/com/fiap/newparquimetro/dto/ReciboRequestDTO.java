package br.com.fiap.newparquimetro.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ReciboRequestDTO(
        String tempo,
        Double valorTotal,
        String condutorId,
        UUID tarifa
) {
}
