package br.com.fiap.funcionalidadeDeCargaDeProdutos.application.service;

import br.com.fiap.funcionalidadeDeCargaDeProdutos.application.dto.ProdutoDTO;
import java.util.List;

public interface ProdutoService {

    ProdutoDTO createProduto(ProdutoDTO produtoDTO);

    ProdutoDTO updateProduto(Long id, ProdutoDTO produtoDTO);

    void deleteProduto(Long id);

    ProdutoDTO getProdutoById(Long id);

    List<ProdutoDTO> getAllProdutos();
}
