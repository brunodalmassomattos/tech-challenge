package br.com.fiap.entrega.application.dto;

import br.com.fiap.entrega.application.enumerator.SituacaoEnum;
import lombok.Builder;

import java.util.UUID;

@Builder
public record EntregaLoteResponseDto(
        UUID id,
        SituacaoEnum situacao,
        EnderecoEntregaDto enderecoEntregaDto,
        String dataHoraEntrega,
        LocalizacaoDto localizacaoDto,
        String entregador,
        String codigoRastreio
) {
}
