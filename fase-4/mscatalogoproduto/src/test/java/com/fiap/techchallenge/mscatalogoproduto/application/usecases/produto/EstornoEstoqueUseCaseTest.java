package com.fiap.techchallenge.mscatalogoproduto.application.usecases.produto;

import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Produto;
import com.fiap.techchallenge.mscatalogoproduto.domain.repositories.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EstornoEstoqueUseCaseTest {
    private final ProdutoRepository repository = mock(ProdutoRepository.class);
    private final EstornoEstoqueUseCase estornoEstoqueUseCase = new EstornoEstoqueUseCase(repository);

    @Test
    void deveEstornarEstoqueComSucesso() {
        UUID produtoId = UUID.randomUUID();
        int quantidadeEstorno = 10;

        Produto produtoEntity = new Produto();
        produtoEntity.setId(produtoId);
        produtoEntity.setNome("Produto Teste");
        produtoEntity.setDescricao("Descrição do Produto Teste");
        produtoEntity.setPreco(BigDecimal.valueOf(100.0));
        produtoEntity.setQtdEstoque(20);

        when(repository.findById(produtoId)).thenReturn(Optional.of(produtoEntity));
        when(repository.save(any(Produto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Produto resultado = estornoEstoqueUseCase.execute(produtoId, quantidadeEstorno);

        assertNotNull(resultado);
        assertEquals(30, resultado.getQtdEstoque()); // 20 + 10
        verify(repository, times(1)).findById(produtoId);
        verify(repository, times(1)).save(produtoEntity);
    }

    @Test
    void deveLancarExcecaoQuandoProdutoNaoEncontrado() {
        UUID produtoId = UUID.randomUUID();
        int quantidadeEstorno = 10;

        when(repository.findById(produtoId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> estornoEstoqueUseCase.execute(produtoId, quantidadeEstorno)
        );

        assertEquals("Produto não encontrado com ID: " + produtoId, exception.getMessage());
        verify(repository, times(1)).findById(produtoId);
        verify(repository, never()).save(any());
    }
}