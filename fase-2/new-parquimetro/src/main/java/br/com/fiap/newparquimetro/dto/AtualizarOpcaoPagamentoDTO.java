package br.com.fiap.newparquimetro.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtualizarOpcaoPagamentoDTO {

    @NotNull(message = "O ID do pagamento é obrigatório.")
    private Long id;

    @NotNull(message = "O tipo de pagamento é obrigatório.")
    private String tipo;

    @NotNull(message = "O status do pagamento é obrigatório.")
    private String status;

    @NotNull(message = "O valor do pagamento é obrigatório.")
    private Double valor;

    @NotNull(message = "O ID do condutor é obrigatório.")
    private String idCondutor;
}

