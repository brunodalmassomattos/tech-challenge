package br.com.fiap.level3.domain.restaurante.core.ports.incoming;

import java.util.UUID;

public interface DeleteRestaurante {
    void deleteRestaurante(UUID id);
}
