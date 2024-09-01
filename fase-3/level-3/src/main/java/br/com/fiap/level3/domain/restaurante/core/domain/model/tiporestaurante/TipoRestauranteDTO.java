package br.com.fiap.level3.domain.restaurante.core.domain.model.tiporestaurante;

public record TipoRestauranteDTO(String id, String descricao) {
    public static TipoRestauranteDTO fromTipoRestaurante(TipoRestaurante tipoRestaurante) {
        return new TipoRestauranteDTO(
                tipoRestaurante.getId().toString(),
                tipoRestaurante.getDescricao());
    }
}
