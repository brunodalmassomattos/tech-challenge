package com.fiap.techchallenge.mscatalogoproduto.application.usecases;

import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Categoria;
import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Produto;
import com.fiap.techchallenge.mscatalogoproduto.domain.repositories.CategoriaRepository;
import com.fiap.techchallenge.mscatalogoproduto.domain.repositories.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class CreateProdutoUseCase {

    private final ProdutoRepository repository;
    private final CategoriaRepository categoriaRepository;

    public CreateProdutoUseCase(ProdutoRepository repository, CategoriaRepository categoriaRepository) {
        this.repository = repository;
        this.categoriaRepository = categoriaRepository;
    }

    public Produto execute(String nome, String descricao, double preco, int qtdEstoque, String idCategoria) {

        Categoria categoria = categoriaRepository.findById(UUID.fromString(idCategoria))
                .orElseThrow(() -> new RuntimeException("Id da Categoria não encontrado: " + idCategoria));

        return repository.save(new Produto(
                nome,
                descricao,
                new BigDecimal(preco),
                qtdEstoque,
                new Categoria(categoria.getId(), categoria.getDescricao())));
    }
}
