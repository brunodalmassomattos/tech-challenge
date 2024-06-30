package br.com.fiap.newparquimetro.dto.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoTarifaEnum {

    FIXO("Fixo"),

    VARIAVEL("Variável");

    private String tipo;
}
