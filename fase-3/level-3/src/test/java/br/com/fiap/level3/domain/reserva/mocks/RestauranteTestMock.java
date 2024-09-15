package br.com.fiap.level3.domain.reserva.mocks;

import br.com.fiap.level3.domain.reserva.core.model.restaurante.Restaurante;

import java.util.UUID;

public class RestauranteTestMock {

    public static Restaurante getRestaurante() {
        return Restaurante.builder()
                       .id(UUID.fromString("d5e6b4b7-c3ab-454d-85bc-f3f0d989d4b3"))
                       .nome("Boi de Ouro")
                       .capacidade(100)
                       .build();
    }
}
