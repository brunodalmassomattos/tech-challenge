package br.com.fiap.newparquimetro.domain.emissaorecibo;

import br.com.fiap.newparquimetro.domain.opcoesDePagamento.OpcoesDePagamento;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recibo")
public class Recibo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "data")
    private LocalDateTime data;

    @OneToOne
    @JoinColumn(name = "pagamento_id")
    private OpcoesDePagamento pagamento;
}