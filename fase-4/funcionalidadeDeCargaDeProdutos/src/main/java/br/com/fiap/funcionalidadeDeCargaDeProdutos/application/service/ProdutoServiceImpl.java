package br.com.fiap.funcionalidadeDeCargaDeProdutos.application.service;

import br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.entity.Produto;
import br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;

    @Override
    public Produto createProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    @Override
    public Produto updateProduto(Long id, Produto produto) {
        Produto existente = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com id " + id));

        existente.setNome(produto.getNome());
        existente.setDescricao(produto.getDescricao());
        existente.setPreco(produto.getPreco());
        existente.setQtdEstoque(produto.getQtdEstoque());
        existente.setCategoria(produto.getCategoria());

        return produtoRepository.save(existente);
    }

    @Override
    public void deleteProduto(Long id) {
        produtoRepository.deleteById(id);
    }

    @Override
    public Produto getProdutoById(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com id " + id));
    }

    @Override
    public List<Produto> getAllProdutos() {
        return produtoRepository.findAll();
    }
}
