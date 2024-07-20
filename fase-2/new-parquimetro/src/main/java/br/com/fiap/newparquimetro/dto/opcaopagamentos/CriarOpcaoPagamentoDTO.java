package br.com.fiap.newparquimetro.dto.opcaopagamentos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
public class CriarOpcaoPagamentoDTO {

    @NotNull(message = "O ID do Tempo é obrigatório.")
    private String idTempo;

    @NotNull(message = "A data do pagamento é obrigatório.")
    private String dataPagamento;

    @NotNull(message = "O status do pagamento é obrigatório.")
    private String status;

    @NotNull(message = "O valor do pagamento é obrigatório.")
    private BigDecimal valor;

    @NotNull(message = "O ID do condutor é obrigatório.")
    private String idCondutor;
}

