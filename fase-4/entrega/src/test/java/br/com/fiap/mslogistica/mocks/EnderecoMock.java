package br.com.fiap.entrega.mocks;

import br.com.fiap.entrega.domain.entity.Endereco;

import java.util.UUID;

public class EnderecoMock {

    public static Endereco getEndereco() {
        return Endereco.builder()
                       .id(UUID.randomUUID())
                       .estado("SP")
                       .cidade("Mirassol")
                       .bairro("Centro")
                       .rua("XV de Novembro")
                       .numero("2045")
                       .cep("15130-023")
                       .build();
    }
}
