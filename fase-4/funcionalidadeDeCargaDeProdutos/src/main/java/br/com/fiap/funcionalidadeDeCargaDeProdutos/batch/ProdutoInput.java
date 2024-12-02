package br.com.fiap.funcionalidadeDeCargaDeProdutos.batch;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ProdutoInput {

    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Integer qtdEstoque;
    private String categoriaDescricao;
}

