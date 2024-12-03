package br.com.fiap.mslogistica.application.dto;

import br.com.fiap.mslogistica.application.enumerator.SituacaoEnum;
import lombok.Builder;

import java.util.UUID;

@Builder
public record EntregaResponseDto(
        UUID id,
        SituacaoEnum situacao,
        String dataHoraEntrega,
        LocalizacaoDto localizacaoDto,
        String entregador,
        String codigoRastreio,
        EnderecoEntregaDto enderecoEntregaDto,
        UUID loteId
) {
}
