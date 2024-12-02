package com.fiap.techchallenge.mscatalogoproduto.domain.entities;

import com.fiap.techchallenge.mscatalogoproduto.infrastructure.persistence.entities.ProdutoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    private UUID id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private int qtdEstoque;
    private Categoria categoria;

    public Produto(ProdutoEntity entity) {
        if (entity.getPreco().compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Preço não pode ser zero ou negativo.");
        if (entity.getQtdEstoque() < 0) throw new IllegalArgumentException("Estoque não pode ser negativo");

        this.id = entity.getId();
        this.nome = entity.getNome();
        this.descricao = entity.getDescricao();
        this.preco = entity.getPreco();
        this.qtdEstoque = entity.getQtdEstoque();
        this.categoria = new Categoria(entity.getCategoriaEntity().getId(), entity.getCategoriaEntity().getDescricao());
    }

    public Produto(String nome, String descricao, BigDecimal preco, int qtdEstoque, Categoria categoria) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.qtdEstoque = qtdEstoque;
        this.categoria = new Categoria(categoria.getId(), categoria.getDescricao());
    }
}
