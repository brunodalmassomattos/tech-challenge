package br.com.fiap.level3.domain.tiporestaurante.core.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public record TipoRestauranteDTO(
        String id,
        @NotNull(message = "O campo descrição é obrigatorio")
        @NotBlank(message = "O campo descrição é obrigatorio")
        String descricao) {

    public static List<TipoRestauranteDTO> fromTipoRestaurante(List<TipoRestaurante> tipoRestaurantes) {
        return tipoRestaurantes
                .stream()
                .map(tipoRestaurante -> {
                    return new TipoRestauranteDTO(tipoRestaurante.getId().toString(), tipoRestaurante.getDescricao());
                })
                .collect(Collectors.toList());
    }

    public static TipoRestauranteDTO fromDTO(TipoRestaurante tipoRestaurante) {
        return new TipoRestauranteDTO(tipoRestaurante.getId().toString(), tipoRestaurante.getDescricao());
    }

}
