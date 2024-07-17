package br.com.fiap.newparquimetro.dto.recibos;

import br.com.fiap.newparquimetro.dto.tarifa.TarifaResponseDTO;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
public record ReciboResponseDTO(
        String id,
        LocalTime tempo,
        BigDecimal valorTotal,
        String nomeCondutor,
        String cpfCnpjCondutor,
        TarifaResponseDTO tarifa,
        LocalDateTime data
) {
}
