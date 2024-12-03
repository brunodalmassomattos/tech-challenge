package br.com.fiap.mslogistica.application.dto;

import br.com.fiap.mslogistica.application.enumerator.SituacaoLoteEnum;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record LoteEntregaResponseDto(
        UUID id,
        SituacaoLoteEnum situacao,
        LocalizacaoDto localizacaoDto,
        String trasportadora,
        List<EntregaLoteResponseDto> entregasDtos
) {
}
