package com.fiap.techchallenge.mscatalogoproduto.application.usecases.produto;

import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Produto;
import com.fiap.techchallenge.mscatalogoproduto.domain.repositories.ProdutoRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetProdutoUseCaseTest {
    private final ProdutoRepository repository = mock(ProdutoRepository.class);
    private final GetProdutoUseCase getProdutoUseCase = new GetProdutoUseCase(repository);

    @Test
    void deveRetornarProdutoComSucesso() {
        UUID produtoId = UUID.randomUUID();

        Produto produtoEntity = new Produto();
        produtoEntity.setId(produtoId);
        produtoEntity.setNome("Produto Teste");
        produtoEntity.setDescricao("Descrição do Produto Teste");

        when(repository.findById(produtoId)).thenReturn(Optional.of(produtoEntity));

        Produto resultado = getProdutoUseCase.execute(produtoId);

        assertNotNull(resultado);
        assertEquals(produtoId, resultado.getId());
        assertEquals("Produto Teste", resultado.getNome());
        assertEquals("Descrição do Produto Teste", resultado.getDescricao());

        verify(repository, times(1)).findById(produtoId);
    }

    @Test
    void deveLancarExcecaoQuandoProdutoNaoEncontrado() {
        UUID produtoId = UUID.randomUUID();

        when(repository.findById(produtoId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> getProdutoUseCase.execute(produtoId)
        );

        assertEquals("Produto não localizado para o id: " + produtoId, exception.getMessage());
        verify(repository, times(1)).findById(produtoId);
    }
}