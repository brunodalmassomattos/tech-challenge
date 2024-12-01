package com.fiap.techchallenge.mscatalogoproduto.application.usecases.produto;


import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Produto;
import com.fiap.techchallenge.mscatalogoproduto.domain.repositories.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BaixaEstoqueProdutoUseCaseTest {
    private final ProdutoRepository repository = mock(ProdutoRepository.class);
    private final BaixaEstoqueProdutoUseCase baixaEstoqueProdutoUseCase = new BaixaEstoqueProdutoUseCase(repository);

    @Test
    void deveRealizarBaixaEstoqueComSucesso() {
        UUID produtoId = UUID.randomUUID();

        Produto produto = new Produto();
        produto.setId(produtoId);
        produto.setNome("Produto Teste");
        produto.setQtdEstoque(10);

        when(repository.findById(produtoId)).thenReturn(Optional.of(produto));
        when(repository.save(any(Produto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Produto resultado = baixaEstoqueProdutoUseCase.execute(produtoId, 5);

        assertNotNull(resultado);
        assertEquals(5, resultado.getQtdEstoque());
        verify(repository, times(1)).findById(produtoId);
        verify(repository, times(1)).save(produto);
    }

    @Test
    void deveLancarExcecaoQuandoProdutoNaoExistir() {
        UUID produtoId = UUID.randomUUID();

        when(repository.findById(produtoId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> baixaEstoqueProdutoUseCase.execute(produtoId, 5)
        );

        assertEquals("Id do Produto não encontrado: " + produtoId, exception.getMessage());
        verify(repository, times(1)).findById(produtoId);
        verify(repository, never()).save(any());
    }

    @Test
    void deveLancarExcecaoQuandoEstoqueInsuficiente() {
        UUID produtoId = UUID.randomUUID();

        Produto produto = new Produto();
        produto.setId(produtoId);
        produto.setNome("Produto Teste");
        produto.setQtdEstoque(3);

        when(repository.findById(produtoId)).thenReturn(Optional.of(produto));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> baixaEstoqueProdutoUseCase.execute(produtoId, 5)
        );

        assertEquals("Quantidade insuficiente no estoque.", exception.getMessage());
        verify(repository, times(1)).findById(produtoId);
        verify(repository, never()).save(any());
    }
}