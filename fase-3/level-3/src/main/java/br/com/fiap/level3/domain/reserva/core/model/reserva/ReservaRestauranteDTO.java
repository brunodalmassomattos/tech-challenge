package br.com.fiap.level3.domain.reserva.core.model.reserva;

import java.util.List;

public record ReservaRestauranteDTO(
        List<ReservaDTO> reservas,
        int totalPessoas,
        int capacidadeRestaurante,
        boolean podeAceitarMaisReservas
) {}
