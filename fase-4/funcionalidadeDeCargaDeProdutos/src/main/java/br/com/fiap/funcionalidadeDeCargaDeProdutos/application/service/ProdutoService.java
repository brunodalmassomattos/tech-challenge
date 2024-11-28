package br.com.fiap.funcionalidadeDeCargaDeProdutos.application.service;

import br.com.fiap.funcionalidadeDeCargaDeProdutos.application.dto.ProdutoDTO;

import java.util.List;
import java.util.UUID;

public interface ProdutoService {

    ProdutoDTO createProduto(ProdutoDTO produtoDTO);

    ProdutoDTO updateProduto(UUID id, ProdutoDTO produtoDTO);

    void deleteProduto(UUID id);

    ProdutoDTO getProdutoById(UUID id);

    List<ProdutoDTO> getAllProdutos();
}
