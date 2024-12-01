package com.fiap.techchallenge.mscatalogoproduto.application.usecases.produto;

import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Produto;
import com.fiap.techchallenge.mscatalogoproduto.domain.repositories.ProdutoRepository;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetAllProdutosUseCaseTest {
    private final ProdutoRepository repository = mock(ProdutoRepository.class);
    private final GetAllProdutosUseCase getAllProdutosUseCase = new GetAllProdutosUseCase(repository);

    @Test
    void deveRetornarTodosOsProdutos() {
        Produto produto1 = new Produto();
        produto1.setId(UUID.randomUUID());
        produto1.setNome("Produto 1");
        produto1.setDescricao("Descrição do Produto 1");

        Produto produto2 = new Produto();
        produto2.setId(UUID.randomUUID());
        produto2.setNome("Produto 2");
        produto2.setDescricao("Descrição do Produto 2");

        List<Produto> produtos = Arrays.asList(produto1, produto2);

        when(repository.findAll()).thenReturn(produtos);

        List<Produto> resultado = getAllProdutosUseCase.execute();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Produto 1", resultado.get(0).getNome());
        assertEquals("Produto 2", resultado.get(1).getNome());

        verify(repository, times(1)).findAll();
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoExistemProdutos() {
        when(repository.findAll()).thenReturn(List.of());

        List<Produto> resultado = getAllProdutosUseCase.execute();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());

        verify(repository, times(1)).findAll();
    }
}