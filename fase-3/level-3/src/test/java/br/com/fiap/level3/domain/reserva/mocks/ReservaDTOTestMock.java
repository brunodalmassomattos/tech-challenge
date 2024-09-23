package br.com.fiap.level3.domain.reserva.mocks;

import br.com.fiap.level3.domain.reserva.core.model.reserva.ReservaDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.util.UUID;

public class ReservaDTOTestMock {

    public static ReservaDTO getReservaDTO() {
        return ReservaDTO.builder()
                       .data(LocalDate.now().plusDays(1))
                       .hora(LocalTime.of(20, 30))
                       .quantidadePessoas(4)
                       .usuarioId(UUID.randomUUID())
                       .restauranteId(UUID.randomUUID())
                       .build();
    }

    public static ReservaDTO getReservaDTOSemDataInformada() {
        return ReservaDTO.builder()
                       .hora(LocalTime.of(20, 30))
                       .quantidadePessoas(4)
                       .usuarioId(UUID.randomUUID())
                       .restauranteId(UUID.randomUUID())
                       .build();
    }

    public static ReservaDTO getReservaDTOSemHoraInformada() {
        return ReservaDTO.builder()
                       .data(LocalDate.now().plusDays(1))
                       .quantidadePessoas(4)
                       .usuarioId(UUID.randomUUID())
                       .restauranteId(UUID.randomUUID())
                       .build();
    }

    public static ReservaDTO getReservaDTOSemQuantidadeDePessoasInformada() {
        return ReservaDTO.builder()
                       .data(LocalDate.now().plusDays(1))
                       .hora(LocalTime.of(20, 30))
                       .usuarioId(UUID.randomUUID())
                       .restauranteId(UUID.randomUUID())
                       .build();
    }

    public static ReservaDTO getReservaDTOComQuantidadeDePessoasMenorQueUm() {
        return ReservaDTO.builder()
                       .data(LocalDate.now().plusDays(1))
                       .hora(LocalTime.of(20, 30))
                       .quantidadePessoas(0)
                       .usuarioId(UUID.randomUUID())
                       .restauranteId(UUID.randomUUID())
                       .build();
    }

    public static ReservaDTO getReservaDTOComDataAnteriorQueAtual() {
        return ReservaDTO.builder()
                       .data(LocalDate.now().minusDays(1))
                       .hora(LocalTime.of(20, 30))
                       .quantidadePessoas(0)
                       .usuarioId(UUID.randomUUID())
                       .restauranteId(UUID.randomUUID())
                       .build();
    }

    public static ReservaDTO getReservaDTOComDataIgualAtualEHoraIgualHoraAtual() {
        return ReservaDTO.builder()
                       .data(LocalDate.now())
                       .hora(LocalTime.now())
                       .quantidadePessoas(0)
                       .usuarioId(UUID.randomUUID())
                       .restauranteId(UUID.randomUUID())
                       .build();
    }
}
