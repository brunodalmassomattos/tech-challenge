package br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.repository;

import br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.entity.Produto;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository {

    Produto save(Produto produto);

    Optional<Produto> findById(Long id);

    List<Produto> findAll();

    void deleteById(Long id);
}
