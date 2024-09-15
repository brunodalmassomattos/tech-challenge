package br.com.fiap.level3.domain.reserva.core.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum StatusEnum {

    CONFIRMADA("CONFIRMADA"), //A reserva foi confirmada pelo restaurante.
    CANCELADA("CANCELADA"), //A reserva foi cancelada pelo cliente ou pelo restaurante.
    AGUARDANDO_CONFIRMACAO("CANCELADA"), //A reserva foi feita, mas ainda não foi confirmada pelo restaurante.
    NAO_COMPARECEU("NAO_COMPARECEU"), //O cliente não apareceu para a reserva.
    EM_ANDAMENTO("EM_ANDAMENTO"), //O cliente chegou e a reserva está em andamento.
    CONCLUIDA("CONCLUIDA"), //A reserva foi concluída com sucesso, e o cliente já saiu.
    ALTERADA("ALTERADA"), //A reserva foi alterada pelo cliente ou pelo restaurante (ex.: mudança de horário, número de pessoas).
    EXPIRADA("EXPIRADA"), //A reserva passou do horário e não foi confirmada, sendo automaticamente expirada.
    ESPERA("ESPERA"), //A reserva foi colocada em uma lista de espera, aguardando disponibilidade de mesa.
    CRIADA("CRIADA"); //Quando a reserva é criada pelo usuário

    private final String descricao;

    public static List<String> getStatusNaoFinalizados() {
        return Stream.of(CRIADA, CONFIRMADA, AGUARDANDO_CONFIRMACAO, ESPERA, EM_ANDAMENTO)
                       .map(StatusEnum::getDescricao)
                       .toList();
    }

    public static List<StatusEnum> getStatusFinalizados() {
        return List.of(CONCLUIDA, EXPIRADA, NAO_COMPARECEU, CANCELADA);
    }
}
