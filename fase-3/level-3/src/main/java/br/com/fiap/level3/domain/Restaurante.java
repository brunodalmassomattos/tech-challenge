package br.com.fiap.level3.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "restaurantes")
public class Restaurante {
    @Id
    @GeneratedValue
    private UUID id;

    private String nome;

    private String horarioFuncionamento;

    private int capacidade;

    @OneToOne
    @JoinColumn(name = "id_endereco")
    private Endereco endereco;

    @OneToOne
    @JoinColumn(name = "id_tipo_restaurante")
    private TipoRestaurante tipoRestaurante;

    private Boolean status;
}

