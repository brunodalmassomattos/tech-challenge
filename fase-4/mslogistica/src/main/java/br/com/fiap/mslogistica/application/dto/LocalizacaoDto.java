package br.com.fiap.mslogistica.application.dto;

import lombok.*;

@Builder
public record LocalizacaoDto(
        String longitude,
        String latitude
) {
}
