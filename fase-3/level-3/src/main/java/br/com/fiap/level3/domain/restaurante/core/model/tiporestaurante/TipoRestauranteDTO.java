package br.com.fiap.level3.domain.restaurante.core.model.tiporestaurante;

import java.util.UUID;

public record TipoRestauranteDTO(String id,
                                 String descricao) {

    public static TipoRestauranteDTO fromTipoRestaurante(TipoRestaurante tipoRestaurante) {
        return new TipoRestauranteDTO(
                tipoRestaurante.getId().toString(),
                tipoRestaurante.getDescricao());
    }

    public static TipoRestaurante toTipoRestaurante(String id, String descricao) {
        return new TipoRestaurante(
                id == null ? null : UUID.fromString(id),
                descricao);
    }
}
