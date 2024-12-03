package br.com.fiap.mslogistica.application.enumerator;

import br.com.fiap.mslogistica.application.exception.ControllerNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum SituacaoLoteEnum {
    PROBLEMA("Problema na entrega"),
    CRIADO("Criado"),
    EM_SEPARACAO("Em separação"),
    DESPACHADO("Despachado"),
    ENTRADA_FILIAL("Entrada na filial");

    private String valor;

    public static SituacaoLoteEnum getSituacaoLoteEnum(String valor) {
        return Stream.of(SituacaoLoteEnum.values())
                       .filter(situacaoLoteEnum -> situacaoLoteEnum.getValor().equals(valor))
                       .findFirst()
                       .orElseThrow(() -> new ControllerNotFoundException(String.format("Situação de lote inválida: %s", valor)));
    }
}
