package br.com.fiap.newparquimetro.dto;

import br.com.fiap.newparquimetro.dto.enums.TipoTarifaEnum;
import lombok.Builder;

@Builder
public record TarifaDTO(
        Double valor,
        TipoTarifaEnum tipo
) { }
