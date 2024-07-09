package br.com.fiap.newparquimetro.dto;

import br.com.fiap.newparquimetro.domain.emissaorecibo.Tarifa;
import br.com.fiap.newparquimetro.dto.enums.TipoTarifaEnum;
import lombok.Builder;

@Builder
public record TarifaResponseDTO(
        String id,
        Double valor,
        TipoTarifaEnum tipo
) {
    public static TarifaResponseDTO toDto(Tarifa tarifa) {
        return TarifaResponseDTO.builder()
                .id(tarifa.getId())
                .tipo(tarifa.getTipo())
                .valor(tarifa.getValor())
                .build();
    }

    public static Tarifa toEntity(TarifaResponseDTO tarifaDto) {
        return Tarifa.builder()
                       .id(tarifaDto.id())
                       .valor(tarifaDto.valor())
                       .tipo(tarifaDto.tipo())
                       .build();
    }
}
