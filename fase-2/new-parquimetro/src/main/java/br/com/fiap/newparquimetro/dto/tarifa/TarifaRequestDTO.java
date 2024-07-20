package br.com.fiap.newparquimetro.dto.tarifa;

import br.com.fiap.newparquimetro.controller.exception.ControllerNotFoundException;
import br.com.fiap.newparquimetro.domain.emissaorecibo.Tarifa;
import br.com.fiap.newparquimetro.dto.enums.TipoTarifaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record TarifaRequestDTO(
        @NotNull(message = "Valor deve ser informado.")
        Double valor,
        @Schema(type = "string", enumAsRef = true, allowableValues = {"VARIAVEL", "FIXO"})
        String tipo) {
    public static Tarifa toEntity(TarifaRequestDTO tarifaDto) {
        return Tarifa.builder()
                .tipo(
                        TipoTarifaEnum.getByDescricao(tarifaDto.tipo())
                                .orElseThrow(() -> new ControllerNotFoundException("Tipo inválido, insira: FIXO ou VARIAVEL")))
                .valor(tarifaDto.valor())
                .build();
    }
}
