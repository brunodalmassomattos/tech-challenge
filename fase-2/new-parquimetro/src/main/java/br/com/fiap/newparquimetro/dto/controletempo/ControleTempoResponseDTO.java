package br.com.fiap.newparquimetro.dto.controletempo;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import br.com.fiap.newparquimetro.domain.condutor.Tempo;

public record ControleTempoResponseDTO(String id,
                                       String Data,
                                       LocalTime hrInicio,
                                       LocalTime hrFim,
                                       String idCondutor,
                                       String status,
                                       String idTarifa) {

    public static String parseDate(Date data) {
        if (data == null) {
            return null;
        }

        return new SimpleDateFormat("yyyy-MM-dd").format(data);
    }

    public static ControleTempoResponseDTO toDto(Tempo tempo) {
        return new ControleTempoResponseDTO(
                tempo.getId(),
                ControleTempoResponseDTO.parseDate(tempo.getData()),
                tempo.getHrInicio(),
                tempo.getHrFim(),
                tempo.getIdCondutor(),
                tempo.getStatus(),
                tempo.getIdTarifa());
    }

    public static List<ControleTempoResponseDTO> toDtoList(List<Tempo> tempos) {
        return tempos.stream()
                .map(ControleTempoResponseDTO::toDto)
                .collect(Collectors.toList());
    }

}
