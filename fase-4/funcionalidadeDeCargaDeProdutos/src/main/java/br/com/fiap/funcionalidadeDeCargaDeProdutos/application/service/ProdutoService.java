package br.com.fiap.funcionalidadeDeCargaDeProdutos.application.service;

import br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.entity.Produto;

import java.util.List;

public interface ProdutoService {

    Produto createProduto(Produto produto);

    Produto updateProduto(Long id, Produto produto);

    void deleteProduto(Long id);

    Produto getProdutoById(Long id);

    List<Produto> getAllProdutos();
}