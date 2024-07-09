package br.com.fiap.newparquimetro.dto;

import br.com.fiap.newparquimetro.domain.emissaorecibo.Tarifa;
import br.com.fiap.newparquimetro.dto.enums.TipoTarifaEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record TarifaRequestDTO(
        @NotBlank(message = "Valor deve ser informado.")
        Double valor,

        @NotBlank(message = "Tipo de tarifa deve ser informado.")
        TipoTarifaEnum tipo
) {
    public static Tarifa toEntity(TarifaRequestDTO tarifaDto) {
        return Tarifa.builder()
                .tipo(tarifaDto.tipo())
                .valor(tarifaDto.valor())
                .build();
    }
}
