package br.com.fiap.entrega.application.dto;

import lombok.*;

@Builder
public record LocalizacaoDto(
        String longitude,
        String latitude
) {
}
