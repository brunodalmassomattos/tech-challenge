package br.com.fiap.newparquimetro.domain.emissaorecibo;

import br.com.fiap.newparquimetro.domain.condutor.Tempo;
import jakarta.persistence.*;
import lombok.*;


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

    @Temporal(TemporalType.TIME)
    @Column(name = "tempo")
    private Long tempo;

    @Column(name = "valor_total")
    private Double valorTotal;

    @Column(name = "condutor_id")
    private String idCondutor;

    @Column(name = "tempo_id")
    private String idTempo;

    @ManyToOne
    @JoinColumn(name = "tarifa_id")
    private Tarifa tarifa;
}