package br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "categorias")
@Getter
@Setter
@NoArgsConstructor
public class Categoria implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NotBlank(message = "A descrição da categoria é obrigatória.")
    @Size(min = 2, max = 100, message = "A descrição deve ter entre 2 e 100 caracteres.")
    @Column(name = "descricao", nullable = false)
    private String descricao;

    public Categoria(String descricao) {
        this.descricao = descricao;
    }
}
