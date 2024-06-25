package br.com.fiap.newparquimetro.domain.opcoesDePagamento;

import br.com.fiap.newparquimetro.domain.condutor.Condutor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "opcoesDePagamento")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpcoesDePagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String tipo;

    private String status;

    private BigDecimal valor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "condutor_id")
    private Condutor condutor;
}
