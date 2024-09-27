package br.com.fiap.level3.domain.restaurante.core.ports.incoming;

import br.com.fiap.level3.domain.restaurante.core.model.tiporestaurante.TipoRestaurante;

import java.util.UUID;

public interface AlterTipoRestaurante {
    void alterTipoRestaurante(UUID idRestaurante, TipoRestaurante tipoRestaurante);
}
