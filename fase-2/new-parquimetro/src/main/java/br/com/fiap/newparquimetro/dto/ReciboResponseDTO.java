package br.com.fiap.newparquimetro.dto;

import br.com.fiap.newparquimetro.domain.emissaorecibo.Recibo;
import lombok.Builder;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

@Builder
public record ReciboResponseDTO(
        String id,
        LocalTime tempo,
        Double valorTotal,
        String nomeCondutor,
        String cpfCnpjCondutor,
        TarifaResponseDTO tarifa
) {
    public static List<ReciboResponseDTO> toDtoList(List<Recibo> recibos, CondutorResponseDTO condutorDto) {
        return recibos.stream()
                       .map(recibo -> toDto(recibo, condutorDto))
                       .toList();
    }

    public static ReciboResponseDTO toDto(Recibo recibo, CondutorResponseDTO condutorDto) {
        return ReciboResponseDTO.builder()
                       .id(recibo.getId())
                       .tempo(getTempoFormatado(recibo.getTempo()))
                       .cpfCnpjCondutor(condutorDto.cpfCnpj())
                       .nomeCondutor(condutorDto.nome())
                       .valorTotal(recibo.getValorTotal())
                       .tarifa(TarifaResponseDTO.toDto(recibo.getTarifa()))
                       .build();
    }


    private static LocalTime getTempoFormatado(Long tempo) {
        Duration minutos = Duration.ofMinutes(tempo);
        long horas = minutos.toHours();
        long minutosRestantes = minutos.minusHours(horas).toMinutes();
        return LocalTime.of((int) horas, (int) minutosRestantes);
    }
}
