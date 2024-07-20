package br.com.fiap.newparquimetro.dto.opcaopagamentos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpcoesDePagamentoListDTO {
    private String id;
    private String status;
    private String dataPagamento;
}

