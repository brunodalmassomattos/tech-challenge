package br.com.fiap.level3.domain.reserva.mocks;

import br.com.fiap.level3.domain.reserva.core.model.restaurante.RestauranteReserva;
import br.com.fiap.level3.domain.restaurante.core.model.endereco.Endereco;
import br.com.fiap.level3.domain.restaurante.core.model.endereco.EnderecoDTO;
import br.com.fiap.level3.domain.restaurante.core.model.restaurante.Restaurante;
import br.com.fiap.level3.domain.restaurante.core.model.restaurante.RestauranteDTO;
import br.com.fiap.level3.domain.restaurante.core.model.tiporestaurante.TipoRestaurante;
import br.com.fiap.level3.domain.restaurante.core.model.tiporestaurante.TipoRestauranteDTO;

import java.util.List;
import java.util.UUID;

public class RestauranteTestMock {

    public static RestauranteReserva getRestaurante() {
        return RestauranteReserva.builder()
                .id(UUID.fromString("d5e6b4b7-c3ab-454d-85bc-f3f0d989d4b3"))
                .nome("Boi de Ouro")
                .capacidade(100)
                .build();
    }

    public static List<Restaurante> buildRestaurantes() {
        return List.of(buildRestaurante(null));
    }

    public static Restaurante buildRestaurante(final UUID id) {
        return Restaurante.builder()
                .id(id == null ? UUID.fromString("37ae4e3c-bdf9-4390-915e-220d5d3348ec") : id)
                .nome("TESTE NOME")
                .horarioFuncionamento("TESTE HORARIO FUNCIONAMENTO")
                .capacidade(100)
                .status(true)
                .tipoRestaurante(buildTipoRestaurante())
                .endereco(
                        Endereco.builder()
                                .id(UUID.fromString("f592e67c-ba51-4207-9bf5-954393f04365"))
                                .cep("12345678")
                                .logradouro("TESTE RUA")
                                .numero("TESTE NUMERO")
                                .bairro("TESTE BAIRRO")
                                .cidade("TESTE CIDADE")
                                .estado("TE")
                                .build())
                .build();
    }

    public static TipoRestaurante buildTipoRestaurante() {
        return TipoRestaurante.builder()
                .id(UUID.fromString("9cbf57e1-52b0-41a3-8fb7-ccc102658bbd"))
                .descricao("TESTE DESCRICAO")
                .build();
    }

    public static RestauranteDTO buildRestauranteDTO() {
        return new RestauranteDTO(
                null,
                "TESTE RESTAURANTE",
                "TESTE HORARIO",
                1,
                true,
                new TipoRestauranteDTO(
                        UUID.randomUUID().toString(),
                        "TESTE DESCRICAO"
                ),
                new EnderecoDTO(
                        null,
                        "Teste CEP",
                        "TESTE RUA",
                        "TESTE NUMERO",
                        "TESTE BAIRRO",
                        "TESTE CIDADE",
                        "TESTE ESTADO")
        );
    }

    public static EnderecoDTO buildEndereco() {
        var id = UUID.randomUUID().toString();
        return new EnderecoDTO(
                id,
                "Teste CEP",
                "TESTE RUA",
                "TESTE NUMERO",
                "TESTE BAIRRO",
                "TESTE CIDADE",
                "TESTE ESTADO"
        );
    }
}
