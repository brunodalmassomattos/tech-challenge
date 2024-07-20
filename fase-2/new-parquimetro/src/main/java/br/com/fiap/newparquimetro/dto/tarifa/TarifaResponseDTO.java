package br.com.fiap.newparquimetro.dto.tarifa;

import br.com.fiap.newparquimetro.domain.emissaorecibo.Tarifa;
import lombok.Builder;

@Builder
public record TarifaResponseDTO(
        String id,
        Double valor,
        String tipo) {

    public static TarifaResponseDTO toDto(Tarifa tarifa) {
        return TarifaResponseDTO.builder()
                .id(tarifa.getId())
                .tipo(tarifa.getTipo().getDescricao())
                .valor(tarifa.getValor())
                .build();
    }
}
