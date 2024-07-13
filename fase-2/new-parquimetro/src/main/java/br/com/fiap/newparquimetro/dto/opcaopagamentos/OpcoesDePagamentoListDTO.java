package br.com.fiap.newparquimetro.dto.opcaopagamentos;

public class OpcoesDePagamentoListDTO {
    private String id;
    private String status;
    private String dataPagamento;

    public OpcoesDePagamentoListDTO(String id, String status, String dataPagamento) {
        this.id = id;
        this.status = status;
        this.dataPagamento = dataPagamento;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getDataPagamento() {
        return dataPagamento;
    }
    public void setDataPagamento(String dataPagamento) {
        this.dataPagamento = dataPagamento;
    }
}

