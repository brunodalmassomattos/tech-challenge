package br.com.fiap.level3.domain.reserva.core.model.reserva;

import br.com.fiap.level3.domain.reserva.core.model.enums.StatusEnum;

public record AtualizarStatusDTO(StatusEnum novoStatus) {}
