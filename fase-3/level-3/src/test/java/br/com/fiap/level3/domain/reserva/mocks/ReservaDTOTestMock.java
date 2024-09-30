package br.com.fiap.level3.domain.reserva.mocks;

import br.com.fiap.level3.domain.reserva.core.model.enums.StatusEnum;
import br.com.fiap.level3.domain.reserva.core.model.reserva.ReservaDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class ReservaDTOTestMock {

    public static ReservaDTO getReservaDTO() {
        return ReservaDTO.builder()
                       .data(LocalDate.now().plusDays(1).format(DateTimeFormatter.ISO_DATE))
                       .hora(LocalTime.of(20, 30).format(DateTimeFormatter.ofPattern("HH:mm")))
                       .quantidadePessoas(4)
                       .usuarioId(UUID.fromString("599abd45-3f86-44b6-8837-fc16f130944e"))
                       .restauranteId(UUID.fromString("d5e6b4b7-c3ab-454d-85bc-f3f0d989d4b3"))
                       .build();
    }

    public static ReservaDTO getReservaDTOSemDataInformada() {
        return ReservaDTO.builder()
                       .hora(LocalTime.of(20, 30).format(DateTimeFormatter.ofPattern("HH:mm")))
                       .quantidadePessoas(4)
                       .usuarioId(UUID.fromString("599abd45-3f86-44b6-8837-fc16f130944e"))
                       .restauranteId(UUID.fromString("d5e6b4b7-c3ab-454d-85bc-f3f0d989d4b3"))
                       .build();
    }

    public static ReservaDTO getReservaDTOSemHoraInformada() {
        return ReservaDTO.builder()
                       .data(LocalDate.now().plusDays(1).format(DateTimeFormatter.ISO_DATE))
                       .quantidadePessoas(4)
                       .usuarioId(UUID.fromString("599abd45-3f86-44b6-8837-fc16f130944e"))
                       .restauranteId(UUID.fromString("d5e6b4b7-c3ab-454d-85bc-f3f0d989d4b3"))
                       .build();
    }

    public static ReservaDTO getReservaDTOSemQuantidadeDePessoasInformada() {
        return ReservaDTO.builder()
                       .data(LocalDate.now().plusDays(1).format(DateTimeFormatter.ISO_DATE))
                       .hora(LocalTime.of(20, 30).format(DateTimeFormatter.ofPattern("HH:mm")))
                       .usuarioId(UUID.fromString("599abd45-3f86-44b6-8837-fc16f130944e"))
                       .restauranteId(UUID.fromString("d5e6b4b7-c3ab-454d-85bc-f3f0d989d4b3"))
                       .build();
    }

    public static ReservaDTO getReservaDTOComQuantidadeDePessoasMenorQueUm() {
        return ReservaDTO.builder()
                       .data(LocalDate.now().plusDays(1).format(DateTimeFormatter.ISO_DATE))
                       .hora(LocalTime.of(20, 30).format(DateTimeFormatter.ofPattern("HH:mm")))
                       .quantidadePessoas(0)
                       .usuarioId(UUID.fromString("599abd45-3f86-44b6-8837-fc16f130944e"))
                       .restauranteId(UUID.fromString("d5e6b4b7-c3ab-454d-85bc-f3f0d989d4b3"))
                       .build();
    }

    public static ReservaDTO getReservaDTOComDataAnteriorQueAtual() {
        return ReservaDTO.builder()
                       .data(LocalDate.now().minusDays(1).format(DateTimeFormatter.ISO_DATE))
                       .hora(LocalTime.of(20, 30).format(DateTimeFormatter.ofPattern("HH:mm")))
                       .quantidadePessoas(0)
                       .usuarioId(UUID.fromString("599abd45-3f86-44b6-8837-fc16f130944e"))
                       .restauranteId(UUID.fromString("d5e6b4b7-c3ab-454d-85bc-f3f0d989d4b3"))
                       .build();
    }

    public static ReservaDTO getReservaDTOComDataIgualAtualEHoraIgualHoraAtual() {
        return ReservaDTO.builder()
                       .data(LocalDate.now().format(DateTimeFormatter.ISO_DATE))
                       .hora(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")))
                       .quantidadePessoas(0)
                       .usuarioId(UUID.fromString("599abd45-3f86-44b6-8837-fc16f130944e"))
                       .restauranteId(UUID.fromString("d5e6b4b7-c3ab-454d-85bc-f3f0d989d4b3"))
                       .build();
    }

    public static ReservaDTO getReservaCriadaComSucesso() {
        return ReservaDTO.builder()
                       .id(UUID.randomUUID())
                       .data("2024-09-24")
                       .hora("20:30")
                       .quantidadePessoas(4)
                       .usuarioId(UUID.fromString("599abd45-3f86-44b6-8837-fc16f130944e"))
                       .restauranteId(UUID.fromString("d5e6b4b7-c3ab-454d-85bc-f3f0d989d4b3"))
                       .status(StatusEnum.CRIADA.getDescricao())
                       .build();
    }

    public static ReservaDTO.ReservaDTOBuilder reservaDTOBuilder() {
        return ReservaDTO.builder()
                       .data(LocalDate.now().plusDays(1).format(DateTimeFormatter.ISO_DATE))
                       .hora(LocalTime.of(20, 30).format(DateTimeFormatter.ofPattern("HH:mm")))
                       .quantidadePessoas(4)
                       .usuarioId(UUID.fromString("599abd45-3f86-44b6-8837-fc16f130944e"))
                       .restauranteId(UUID.fromString("d5e6b4b7-c3ab-454d-85bc-f3f0d989d4b3"));
    }
}
