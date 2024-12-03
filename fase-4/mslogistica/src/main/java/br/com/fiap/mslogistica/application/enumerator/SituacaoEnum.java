package br.com.fiap.mslogistica.application.enumerator;

import br.com.fiap.mslogistica.application.exception.ControllerNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum SituacaoEnum {
    PROBLEMA("Problema na entrega"),
    CRIADO("Criado"),
    EM_SEPARACAO("Em separação"),
    DESPACHADO("Despachado"),
    ENTRADA_FILIAL("Entrada na filial"),
    EM_ROTA_ENTREGA("Em rota de entrega"),
    ENTREGUE("Entregue ao destino");

    private String valor;

    public static SituacaoEnum getSituacao(String valor) {
        return Stream.of(SituacaoEnum.values())
                       .filter(situacaoEnum -> situacaoEnum.valor.equals(valor))
                       .findFirst()
                       .orElseThrow(() -> new ControllerNotFoundException(String.format("Situação de entrega inválida: %s", valor)));
    }
}
