package br.com.fiap.newparquimetro.domain.emissaorecibo;

import br.com.fiap.newparquimetro.domain.condutor.Condutor;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.util.UUID;


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
    private UUID id;

    @Temporal(TemporalType.TIME)
    @Column(name = "tempo")
    private Time tempo;

    @Column(name = "valor_total")
    private Double valorTotal;

    @ManyToOne
    @JoinColumn(name = "condutor_id")
    private Condutor condutor;

    @OneToOne
    @JoinColumn(name = "tarifa_id")
    private Tarifa tarifa;
}