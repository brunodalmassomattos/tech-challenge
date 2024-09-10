package br.com.fiap.level3.domain.restaurante.core.model.restaurante;

import java.util.UUID;

public record AlterarRestauranteDTO(
        String nome,
        String horarioFuncionamento,
        int capacidade) {

    public static Restaurante toRestaurante(String id, AlterarRestauranteDTO alterarRestauranteDTO) {
        return new Restaurante(
                UUID.fromString(id),
                alterarRestauranteDTO.nome,
                alterarRestauranteDTO.horarioFuncionamento,
                alterarRestauranteDTO.capacidade);
    }
}
