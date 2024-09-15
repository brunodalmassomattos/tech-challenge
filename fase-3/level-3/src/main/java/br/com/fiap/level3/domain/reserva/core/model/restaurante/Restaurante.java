package br.com.fiap.level3.domain.reserva.core.model.restaurante;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "restaurantes")
public class Restaurante {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    private String nome;

    private Integer capacidade;

}
