package com.fiap.techchallenge.mscatalogoproduto.application.usecases.produto;

import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Produto;
import com.fiap.techchallenge.mscatalogoproduto.domain.repositories.ProdutoRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SearchProdutosUseCaseTest {

    private final ProdutoRepository repository = mock(ProdutoRepository.class);
    private final SearchProdutosUseCase searchProdutosUseCase = new SearchProdutosUseCase(repository);

    @Test
    void deveRetornarProdutosFiltradosPorNome() {
        String nome = "produto";
        String descricao = null;

        Produto produto = new Produto();
        produto.setId(UUID.randomUUID());
        produto.setNome("Produto Teste");
        produto.setDescricao("Descrição Teste");
        produto.setPreco(BigDecimal.valueOf(100.0));
        produto.setQtdEstoque(10);

        when(repository.findByNameOrDescription(nome.toLowerCase(), null))
                .thenReturn(List.of(produto));

        List<Produto> produtos = searchProdutosUseCase.execute(nome, descricao);

        assertNotNull(produtos);
        assertEquals(1, produtos.size());
        assertEquals("Produto Teste", produtos.get(0).getNome());
        assertEquals("Descrição Teste", produtos.get(0).getDescricao());

        verify(repository, times(1)).findByNameOrDescription(nome.toLowerCase(), null);
    }

    @Test
    void deveRetornarProdutosFiltradosPorDescricao() {
        String nome = null;
        String descricao = "teste";

        Produto produto = new Produto();
        produto.setId(UUID.randomUUID());
        produto.setNome("Produto Teste");
        produto.setDescricao("Descrição Teste");
        produto.setPreco(BigDecimal.valueOf(100.0));
        produto.setQtdEstoque(10);

        when(repository.findByNameOrDescription(null, descricao.toLowerCase()))
                .thenReturn(List.of(produto));

        List<Produto> produtos = searchProdutosUseCase.execute(nome, descricao);

        assertNotNull(produtos);
        assertEquals(1, produtos.size());
        assertEquals("Produto Teste", produtos.get(0).getNome());
        assertEquals("Descrição Teste", produtos.get(0).getDescricao());

        verify(repository, times(1)).findByNameOrDescription(null, descricao.toLowerCase());
    }

    @Test
    void deveRetornarProdutosFiltradosPorNomeEDescricao() {
        String nome = "produto";
        String descricao = "teste";

        Produto produto = new Produto();
        produto.setId(UUID.randomUUID());
        produto.setNome("Produto Teste");
        produto.setDescricao("Descrição Teste");
        produto.setPreco(BigDecimal.valueOf(100.0));
        produto.setQtdEstoque(10);

        when(repository.findByNameOrDescription(nome.toLowerCase(), descricao.toLowerCase()))
                .thenReturn(List.of(produto));

        List<Produto> produtos = searchProdutosUseCase.execute(nome, descricao);

        assertNotNull(produtos);
        assertEquals(1, produtos.size());
        assertEquals("Produto Teste", produtos.get(0).getNome());
        assertEquals("Descrição Teste", produtos.get(0).getDescricao());

        verify(repository, times(1)).findByNameOrDescription(nome.toLowerCase(), descricao.toLowerCase());
    }

    @Test
    void deveRetornarListaVaziaQuandoNenhumProdutoEncontrado() {
        String nome = "produto";
        String descricao = "inexistente";

        when(repository.findByNameOrDescription(nome.toLowerCase(), descricao.toLowerCase()))
                .thenReturn(List.of());

        List<Produto> produtos = searchProdutosUseCase.execute(nome, descricao);

        assertNotNull(produtos);
        assertTrue(produtos.isEmpty());

        verify(repository, times(1)).findByNameOrDescription(nome.toLowerCase(), descricao.toLowerCase());
    }
}