package br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Categoria implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A descrição da categoria é obrigatória.")
    @Size(min = 2, max = 100, message = "A descrição deve ter entre 2 e 100 caracteres.")
    private String descricao;

    public Categoria(String descricao) {
        this.descricao = descricao;
    }
}