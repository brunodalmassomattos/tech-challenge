package br.com.fiap.level3.domain.reserva.mocks;

import br.com.fiap.level3.domain.reserva.core.model.reserva.Reserva;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservaTestMock {

    public static Reserva getReserva() {
        return Reserva.builder()
                       .data(LocalDate.of(2024, 9, 25))
                       .hora(LocalTime.of(20, 30))
                       .quantidadePessoas(4)
                       .usuario(UsuarioTestMock.getUsuario())
                       .restaurante(RestauranteTestMock.getRestaurante())
                       .build();
    }
}
