package br.com.fiap.level3.domain.restaurante.core.model.tiporestaurante;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
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
