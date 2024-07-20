package br.com.fiap.newparquimetro.domain.emissaorecibo;

import br.com.fiap.newparquimetro.dto.enums.TipoTarifaEnum;
import jakarta.persistence.*;
import lombok.*;
import org.apache.catalina.filters.RemoteCIDRFilter;

import java.util.UUID;

@Entity
@Table(name = "tarifa")

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    private Double valor;

    @Enumerated(EnumType.STRING)
    private TipoTarifaEnum tipo;
}