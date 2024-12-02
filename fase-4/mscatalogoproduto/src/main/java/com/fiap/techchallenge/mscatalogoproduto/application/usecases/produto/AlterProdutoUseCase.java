package com.fiap.techchallenge.mscatalogoproduto.application.usecases.produto;

import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Categoria;
import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Produto;
import com.fiap.techchallenge.mscatalogoproduto.domain.repositories.CategoriaRepository;
import com.fiap.techchallenge.mscatalogoproduto.domain.repositories.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AlterProdutoUseCase {

    private final ProdutoRepository repository;
    private final CategoriaRepository categoriaRepository;

    public AlterProdutoUseCase(ProdutoRepository repository, CategoriaRepository categoriaRepository) {
        this.repository = repository;
        this.categoriaRepository = categoriaRepository;
    }

    public Produto execute(Produto produtoRequest) {
        return repository.save(buildProduto(produtoRequest));
    }

    private Produto buildProduto(Produto produtoRequest) {
        Produto produto = repository.findById(produtoRequest.getId())
                .orElseThrow(() -> new EntityNotFoundException("Id do Produto não encontrado: " + produtoRequest.getId()));

        return new Produto(
                produto.getId(),
                produtoRequest.getNome() == null || produtoRequest.getNome().isEmpty() ? produto.getNome() : produtoRequest.getNome(),
                produtoRequest.getDescricao() == null || produtoRequest.getDescricao().isEmpty() ? produto.getDescricao() : produtoRequest.getDescricao(),
                produtoRequest.getPreco().compareTo(BigDecimal.ZERO) <= 0 ? produto.getPreco() : produtoRequest.getPreco(),
                produtoRequest.getQtdEstoque() <= 0 ? produto.getQtdEstoque() : produtoRequest.getQtdEstoque(),
                buildCategoria(produtoRequest, produto.getCategoria()));
    }

    private Categoria buildCategoria(Produto produtoRequest, Categoria categoria) {
        if (produtoRequest.getCategoria().getId() != null) {
            categoria = categoriaRepository.findById(produtoRequest.getCategoria().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Id da Categoria não encontrado: " + produtoRequest.getCategoria().getId()));
        }

        return new Categoria(
                produtoRequest.getCategoria().getId() == null ? categoria.getId() : produtoRequest.getCategoria().getId(),
                produtoRequest.getCategoria().getDescricao() == null || produtoRequest.getCategoria().getDescricao().isEmpty()
                        ? categoria.getDescricao()
                        : produtoRequest.getCategoria().getDescricao());
    }
}
