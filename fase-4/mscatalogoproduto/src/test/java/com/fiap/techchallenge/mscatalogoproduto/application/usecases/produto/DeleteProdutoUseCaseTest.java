package com.fiap.techchallenge.mscatalogoproduto.application.usecases.produto;

import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Produto;
import com.fiap.techchallenge.mscatalogoproduto.domain.repositories.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteProdutoUseCaseTest {


    private final ProdutoRepository repository = mock(ProdutoRepository.class);
    private final DeleteProdutoUseCase deleteProdutoUseCase = new DeleteProdutoUseCase(repository);

    @Test
    void deveExcluirProdutoComSucesso() {
        UUID produtoId = UUID.randomUUID();

        Produto produto = new Produto();
        produto.setId(produtoId);
        produto.setNome("Produto Teste");
        produto.setDescricao("Descrição do Produto Teste");

        when(repository.findById(produtoId)).thenReturn(Optional.of(produto));
        doNothing().when(repository).delete(produto);

        deleteProdutoUseCase.execute(produtoId);

        verify(repository, times(1)).findById(produtoId);
        verify(repository, times(1)).delete(produto);
    }

    @Test
    void deveLancarExcecaoQuandoProdutoNaoEncontrado() {
        UUID produtoId = UUID.randomUUID();

        when(repository.findById(produtoId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> deleteProdutoUseCase.execute(produtoId)
        );

        assertEquals("Produto não encontrado com ID: " + produtoId, exception.getMessage());
        verify(repository, times(1)).findById(produtoId);
        verify(repository, never()).delete(any());
    }
}