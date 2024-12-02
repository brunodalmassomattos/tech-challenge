package com.fiap.techchallenge.mscatalogoproduto.application.usecases.produto;

import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Categoria;
import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Produto;
import com.fiap.techchallenge.mscatalogoproduto.domain.repositories.CategoriaRepository;
import com.fiap.techchallenge.mscatalogoproduto.domain.repositories.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class AlterProdutoUseCaseTest {

    private final ProdutoRepository produtoRepository = mock(ProdutoRepository.class);
    private final CategoriaRepository categoriaRepository = mock(CategoriaRepository.class);
    private final AlterProdutoUseCase alterProdutoUseCase = new AlterProdutoUseCase(produtoRepository, categoriaRepository);

    @Test
    void deveAlterarProdutoComSucesso() {
        UUID produtoId = UUID.randomUUID();
        UUID categoriaId = UUID.randomUUID();

        Produto produtoRequest = new Produto(
                produtoId,
                "Novo Nome",
                "Nova Descrição",
                BigDecimal.valueOf(99.99),
                10,
                new Categoria(categoriaId, "Nova Categoria"));

        Produto produto = new Produto();
        produto.setId(produtoId);
        produto.setNome("Nome Antigo");
        produto.setDescricao("Descrição Antiga");
        produto.setPreco(BigDecimal.valueOf(49.99));
        produto.setQtdEstoque(5);
        
        Categoria categoria = new Categoria();
        categoria.setId(categoriaId);
        categoria.setDescricao("Categoria Antiga");
        produto.setCategoria(categoria);

        when(produtoRepository.findById(produtoId)).thenReturn(Optional.of(produto));
        when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.of(categoria));
        when(produtoRepository.save(any(Produto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Produto resultado = alterProdutoUseCase.execute(produtoRequest);

        assertNotNull(resultado);
        assertEquals(produtoId, resultado.getId());
        assertEquals("Novo Nome", resultado.getNome());
        assertEquals("Nova Descrição", resultado.getDescricao());
        assertEquals(BigDecimal.valueOf(99.99), resultado.getPreco());
        assertEquals(10, resultado.getQtdEstoque());
        assertEquals(categoriaId, resultado.getCategoria().getId());
        assertEquals("Nova Categoria", resultado.getCategoria().getDescricao());

        verify(produtoRepository, times(1)).findById(produtoId);
        verify(categoriaRepository, times(1)).findById(categoriaId);
        verify(produtoRepository, times(1)).save(any(Produto.class));
    }

    @Test
    void deveLancarExcecaoQuandoProdutoNaoExistir() {
        UUID produtoId = UUID.randomUUID();
        Produto produtoRequest = new Produto(produtoId, "Nome", "Descrição", BigDecimal.TEN, 5, null);

        when(produtoRepository.findById(produtoId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> alterProdutoUseCase.execute(produtoRequest)
        );

        assertEquals("Id do Produto não encontrado: " + produtoId, exception.getMessage());
        verify(produtoRepository, times(1)).findById(produtoId);
        verify(categoriaRepository, never()).findById(any());
        verify(produtoRepository, never()).save(any());
    }

    @Test
    void deveLancarExcecaoQuandoCategoriaNaoExistir() {
        UUID produtoId = UUID.randomUUID();
        UUID categoriaId = UUID.randomUUID();

        Produto produtoRequest = new Produto(
                produtoId,
                "Nome",
                "Descrição",
                BigDecimal.TEN,
                5,
                new Categoria(categoriaId, "Nova Categoria"));

        Produto Produto = new Produto();
        Produto.setId(produtoId);
        Produto.setNome("Nome Antigo");

        when(produtoRepository.findById(produtoId)).thenReturn(Optional.of(Produto));
        when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> alterProdutoUseCase.execute(produtoRequest)
        );

        assertEquals("Id da Categoria não encontrado: " + categoriaId, exception.getMessage());
        verify(produtoRepository, times(1)).findById(produtoId);
        verify(categoriaRepository, times(1)).findById(categoriaId);
        verify(produtoRepository, never()).save(any());
    }

    @Test
    void deveManterValoresAntigosQuandoCamposNaoForemInformados() {
        UUID produtoId = UUID.randomUUID();

        Produto produtoRequest = new Produto(
                produtoId,
                "",
                "",
                BigDecimal.ZERO,
                0,
                new Categoria(null, ""));

        Produto Produto = new Produto();
        Produto.setId(produtoId);
        Produto.setNome("Nome Antigo");
        Produto.setDescricao("Descrição Antiga");
        Produto.setPreco(BigDecimal.valueOf(49.99));
        Produto.setQtdEstoque(5);
        Categoria Categoria = new Categoria();
        Categoria.setId(UUID.randomUUID());
        Categoria.setDescricao("Categoria Antiga");
        Produto.setCategoria(Categoria);

        when(produtoRepository.findById(produtoId)).thenReturn(Optional.of(Produto));
        when(produtoRepository.save(any(Produto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Produto resultado = alterProdutoUseCase.execute(produtoRequest);

        assertNotNull(resultado);
        assertEquals(produtoId, resultado.getId());
        assertEquals("Nome Antigo", resultado.getNome());
        assertEquals("Descrição Antiga", resultado.getDescricao());
        assertEquals(BigDecimal.valueOf(49.99), resultado.getPreco());
        assertEquals(5, resultado.getQtdEstoque());
        assertEquals(Categoria.getId(), resultado.getCategoria().getId());
        assertEquals("Categoria Antiga", resultado.getCategoria().getDescricao());

        verify(produtoRepository, times(1)).findById(produtoId);
        verify(produtoRepository, times(1)).save(any(Produto.class));
    }
}