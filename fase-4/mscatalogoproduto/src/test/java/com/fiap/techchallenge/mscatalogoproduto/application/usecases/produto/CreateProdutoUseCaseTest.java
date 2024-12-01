package com.fiap.techchallenge.mscatalogoproduto.application.usecases.produto;

import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Categoria;
import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Produto;
import com.fiap.techchallenge.mscatalogoproduto.domain.repositories.CategoriaRepository;
import com.fiap.techchallenge.mscatalogoproduto.domain.repositories.ProdutoRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateProdutoUseCaseTest {

    private final ProdutoRepository produtoRepository = mock(ProdutoRepository.class);
    private final CategoriaRepository categoriaRepository = mock(CategoriaRepository.class);
    private final CreateProdutoUseCase createProdutoUseCase = new CreateProdutoUseCase(produtoRepository, categoriaRepository);

    @Test
    void deveCriarProdutoComSucesso() {
        String nome = "Produto Teste";
        String descricao = "Descrição do Produto Teste";
        double preco = 99.99;
        int qtdEstoque = 10;

        String idCategoria = UUID.randomUUID().toString();

        Categoria categoria = new Categoria();
        categoria.setId(UUID.fromString(idCategoria));
        categoria.setDescricao("Categoria Teste");

        Produto produto = new Produto();
        produto.setId(UUID.randomUUID());
        produto.setNome(nome);
        produto.setDescricao(descricao);
        produto.setPreco(BigDecimal.valueOf(preco));
        produto.setQtdEstoque(qtdEstoque);
        produto.setCategoria(categoria);

        when(categoriaRepository.findById(UUID.fromString(idCategoria))).thenReturn(Optional.of(categoria));
        when(produtoRepository.save(any(Produto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Produto resultado = createProdutoUseCase.execute(nome, descricao, preco, qtdEstoque, idCategoria);

        assertNotNull(resultado);
        assertEquals(nome, resultado.getNome());
        assertEquals(descricao, resultado.getDescricao());
        assertEquals(BigDecimal.valueOf(preco), resultado.getPreco().setScale(2, RoundingMode.HALF_DOWN));
        assertEquals(qtdEstoque, resultado.getQtdEstoque());
        assertEquals(UUID.fromString(idCategoria), resultado.getCategoria().getId());
        assertEquals("Categoria Teste", resultado.getCategoria().getDescricao());

        verify(categoriaRepository, times(1)).findById(UUID.fromString(idCategoria));
        verify(produtoRepository, times(1)).save(any(Produto.class));
    }

    @Test
    void deveLancarExcecaoQuandoCategoriaNaoExistir() {
        String nome = "Produto Teste";
        String descricao = "Descrição do Produto Teste";
        double preco = 99.99;
        int qtdEstoque = 10;
        String idCategoria = UUID.randomUUID().toString();

        when(categoriaRepository.findById(UUID.fromString(idCategoria))).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> createProdutoUseCase.execute(nome, descricao, preco, qtdEstoque, idCategoria)
        );

        assertEquals("Id da Categoria não encontrado: " + idCategoria, exception.getMessage());
        verify(categoriaRepository, times(1)).findById(UUID.fromString(idCategoria));
        verify(produtoRepository, never()).save(any());
    }
}