package br.com.fiap.level3.domain.restaurante.core.domain.model.tiporestaurante;

import java.util.UUID;

public record AlterarTipoRestauranteDTO(String descricao) {

    public static TipoRestaurante toTipoRestaurante(String id, AlterarTipoRestauranteDTO dto) {
        TipoRestaurante tipoRestaurante = new TipoRestaurante(UUID.fromString(id), dto.descricao);
        return tipoRestaurante;
    }
}

