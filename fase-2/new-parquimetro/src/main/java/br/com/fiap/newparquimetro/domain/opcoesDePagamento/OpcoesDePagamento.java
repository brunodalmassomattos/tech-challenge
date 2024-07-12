package br.com.fiap.newparquimetro.domain.opcoesDePagamento;

import br.com.fiap.newparquimetro.domain.condutor.Condutor;
import br.com.fiap.newparquimetro.dto.opcaopagamentos.AtualizarOpcaoPagamentoDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "pagamentos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpcoesDePagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "tempo_id")
    private String idTempo;

    private String dataPagamento;

    private String status;

    private BigDecimal valor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "condutor_id")
    private Condutor condutor;

    public void atualizarDados(AtualizarOpcaoPagamentoDTO dados) {
        this.idTempo = dados.getIdTempo();
        this.dataPagamento = dados.getDataPagamento();
        this.status = dados.getStatus();
        this.valor = BigDecimal.valueOf(dados.getValor());

        if (!this.condutor.getId().equals(dados.getIdCondutor())) {
            this.condutor = new Condutor();
            this.condutor.setId(dados.getIdCondutor());
        }
    }
}
