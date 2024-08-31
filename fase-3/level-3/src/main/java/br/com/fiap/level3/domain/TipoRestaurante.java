package br.com.fiap.level3.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "tipos_restaurantes")
public class TipoRestaurante {
    @Id
    @GeneratedValue
    private UUID id;

    private String descricao;
}
