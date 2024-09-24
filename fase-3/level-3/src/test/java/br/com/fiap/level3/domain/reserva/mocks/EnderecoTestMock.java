package br.com.fiap.level3.domain.reserva.mocks;

import br.com.fiap.level3.domain.restaurante.core.model.endereco.Endereco;

import java.util.UUID;

public class EnderecoTestMock {

    public static Endereco buildEndereco(UUID id) {
        return new Endereco(
                id,
                "12345-678",
                "Rua Principal",
                "123",
                "Bairro da Vila",
                "Cidade",
                "Estado"
        );
    }
}
