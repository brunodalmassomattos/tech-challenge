package br.com.fiap.newparquimetro.dto.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
@AllArgsConstructor
public enum TipoTarifaEnum {

    FIXO("Fixo"),

    VARIAVEL("Variável");

    private String descricao;

    public static Optional<TipoTarifaEnum> getByDescricao(String descricao) {
        return Arrays.stream(TipoTarifaEnum.values())
                .filter(tipoTarifaEnum -> tipoTarifaEnum.getDescricao().equals(descricao))
                .findFirst();
    }
}
