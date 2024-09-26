package br.com.fiap.level3.domain.reserva.core.model.reserva;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Builder
@JsonIgnoreProperties(value = {"id","status"}, allowGetters = true)
public record ReservaDTO(

        UUID id,
        @JsonFormat(pattern = "yyyy-MM-dd")
        String data,
        @JsonFormat(pattern = "HH:mm")
        String hora,
        Integer quantidadePessoas,
        UUID restauranteId,
        UUID usuarioId,
        String status
) {

    public static ReservaDTO convertFromReserva(Reserva reserva) {
        return ReservaDTO.builder()
                       .id(reserva.getId())
                       .data(reserva.getData().toString())
                       .hora(reserva.getHora().toString())
                       .quantidadePessoas(reserva.getQuantidadePessoas())
                       .restauranteId(reserva.getRestaurante().getId())
                       .usuarioId(reserva.getUsuario().getId())
                       .status(reserva.getStatus())
                       .build();
    }


}
