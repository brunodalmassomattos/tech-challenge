package com.fiap.techchallenge.mscatalogoproduto.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cascade;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity(name = "produtos")
public class ProdutoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private int qtdEstoque;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "categoria_id")
    private CategoriaEntity categoriaEntity;

}
