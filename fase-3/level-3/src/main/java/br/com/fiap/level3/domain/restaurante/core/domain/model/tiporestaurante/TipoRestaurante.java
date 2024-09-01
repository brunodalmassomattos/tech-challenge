package br.com.fiap.level3.domain.restaurante.core.domain.model.tiporestaurante;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoRestaurante {
    private UUID id;
    private String descricao;

    public TipoRestaurante(UUID id) {
        this.id = id;
    }
}
