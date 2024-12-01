package com.fiap.techchallenge.mscatalogoproduto.application.controller;

import com.fiap.techchallenge.mscatalogoproduto.application.dtos.produto.CategoriaResponse;
import com.fiap.techchallenge.mscatalogoproduto.application.dtos.produto.ProdutoResponse;
import com.fiap.techchallenge.mscatalogoproduto.application.usecases.produto.*;
import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Categoria;
import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Produto;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProdutoController.class)
class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetAllProdutosUseCase getAllProdutosUseCase;

    @MockBean
    private GetProdutoUseCase getProdutoUseCase;

    @MockBean
    private SearchProdutosUseCase searchProdutosUseCase;

    @MockBean
    private CreateProdutoUseCase createProdutoUseCase;

    @MockBean
    private AlterProdutoUseCase alterProdutoUseCase;

    @MockBean
    private BaixaEstoqueProdutoUseCase baixaEstoqueProdutoUseCase;

    @MockBean
    private EstornoEstoqueUseCase estornoEstoqueUseCase;

    @MockBean
    private DeleteProdutoUseCase deleteProdutoUseCase;

    @Nested
    class testeCriacaoProduto {

        @Test
        public void testCreateProduto_Success() throws Exception {
            UUID categoriaId = UUID.randomUUID();

            ProdutoResponse produtoResponse = new ProdutoResponse(
                    UUID.randomUUID().toString(),
                    "Produto Teste",
                    "Descrição Teste",
                    BigDecimal.valueOf(100.0),
                    10,
                    new CategoriaResponse(categoriaId.toString(), "Categoria Teste")
            );

            // Simula sucesso no CreateProdutoUseCase
            when(createProdutoUseCase.execute(anyString(), anyString(), anyDouble(), anyInt(), anyString()))
                    .thenReturn(new Produto(
                            UUID.randomUUID(),
                            "Produto Teste",
                            "Descrição Teste",
                            BigDecimal.valueOf(100.0),
                            10,
                            new Categoria(UUID.randomUUID(), "Categoria Teste")
                    ));

            mockMvc.perform(post("/produtos")
                            .contentType("application/json")
                            .content("{\"nome\": \"Produto Teste\", \"descricao\": \"Descrição Teste\", \"preco\": 100.0, \"quantidadeEstoque\": 10, \"idCategoria\": \"" + categoriaId.toString() + "\"}"))
                    .andExpect(status().isCreated());
        }

        @Test
        public void testCreateProduto_EntityNotFoundException() throws Exception {
            // Simula uma EntityNotFoundException quando o CreateProdutoUseCase é chamado
            when(createProdutoUseCase.execute(anyString(), anyString(), anyDouble(), anyInt(), anyString()))
                    .thenThrow(new EntityNotFoundException("Categoria não encontrada"));

            mockMvc.perform(post("/produtos")
                            .contentType("application/json")
                            .content("{\"nome\": \"Produto Teste\", \"descricao\": \"Descrição Teste\", \"preco\": 100.0, \"quantidadeEstoque\": 10, \"idCategoria\": \"1234-5678-90ab-cdef\"}"))
                    .andExpect(status().isNotFound()) // Status HTTP 404
                    .andExpect(content().string("Categoria não encontrada"));
        }

        // Teste de criação de produto com RuntimeException
        @Test
        public void testCreateProduto_RuntimeException() throws Exception {
            // Simula uma RuntimeException no CreateProdutoUseCase
            when(createProdutoUseCase.execute(anyString(), anyString(), anyDouble(), anyInt(), anyString()))
                    .thenThrow(new RuntimeException("Erro inesperado ao criar produto"));

            mockMvc.perform(post("/produtos")
                            .contentType("application/json")
                            .content("{\"nome\": \"Produto Teste\", \"descricao\": \"Descrição Teste\", \"preco\": 100.0, \"quantidadeEstoque\": 10, \"idCategoria\": \"1234-5678-90ab-cdef\"}"))
                    .andExpect(status().isInternalServerError());
        }
    }

    @Nested
    class testeBuscarProdutos {

        @Test
        public void testGetProdutos_Success() throws Exception {
            UUID categoriaId = UUID.randomUUID();
            List<Produto> produtos = List.of(
                    new Produto(UUID.randomUUID(), "Produto 1", "Descrição 1", BigDecimal.valueOf(100.0), 10, new Categoria(categoriaId, "Categoria Teste")),
                    new Produto(UUID.randomUUID(), "Produto 2", "Descrição 2", BigDecimal.valueOf(200.0), 20, new Categoria(categoriaId, "Categoria Teste"))
            );

            // Simula sucesso no GetAllProdutosUseCase
            when(getAllProdutosUseCase.execute()).thenReturn(produtos);

            mockMvc.perform(get("/produtos"))
                    .andExpect(status().isOk());
        }

        @Test
        public void testGetProduto_Success() throws Exception {
            UUID produtoId = UUID.randomUUID();
            UUID categoriaId = UUID.randomUUID();

            ProdutoResponse produtoResponse = new ProdutoResponse(
                    produtoId.toString(),
                    "Produto Teste",
                    "Descrição Teste",
                    BigDecimal.valueOf(100.0),
                    10,
                    new CategoriaResponse(categoriaId.toString(), "Categoria Teste")
            );

            // Simula sucesso no GetProdutoUseCase
            when(getProdutoUseCase.execute(produtoId)).thenReturn(new Produto(
                    produtoId,
                    "Produto Teste",
                    "Descrição Teste",
                    BigDecimal.valueOf(100.0),
                    10,
                    new Categoria(categoriaId, "Categoria Teste")
            ));

            mockMvc.perform(get("/produtos/{id}", produtoId))
                    .andExpect(status().isOk());
        }

        // Teste de busca de produto com EntityNotFoundException
        @Test
        public void testGetProduto_EntityNotFoundException() throws Exception {
            UUID produtoId = UUID.randomUUID();

            // Simula que o GetProdutoUseCase lança uma EntityNotFoundException
            when(getProdutoUseCase.execute(produtoId)).thenThrow(new EntityNotFoundException("Produto não encontrado"));

            mockMvc.perform(get("/produtos/{id}", produtoId))
                    .andExpect(status().isNotFound()) // Status HTTP 404
                    .andExpect(content().string("Produto não encontrado"));
        }
    }

    @Nested
    class testeDeletaProdutos {
        @Test
        public void testDeleteProduto_Success() throws Exception {
            UUID produtoId = UUID.randomUUID();

            // Simula sucesso no DeleteProdutoUseCase
            doNothing().when(deleteProdutoUseCase).execute(produtoId);

            mockMvc.perform(delete("/produtos/{id}", produtoId))
                    .andExpect(status().isNoContent()); // Status HTTP 204
        }

        @Test
        public void testDeleteProduto_EntityNotFoundException() throws Exception {
            UUID produtoId = UUID.randomUUID();

            // Simula que o DeleteProdutoUseCase lança uma EntityNotFoundException
            doThrow(new EntityNotFoundException("Produto não encontrado")).when(deleteProdutoUseCase).execute(produtoId);

            mockMvc.perform(delete("/produtos/{id}", produtoId))
                    .andExpect(status().isNotFound()) // Status HTTP 404
                    .andExpect(content().string("Produto não encontrado"));
        }
    }

    @Nested
    class testeAlteraProdutos {

        @Test
        public void testAlterProduto_Success() throws Exception {
            UUID produtoId = UUID.randomUUID();
            UUID categoriaId = UUID.randomUUID();

            ProdutoResponse produtoResponse = new ProdutoResponse(
                    produtoId.toString(),
                    "Produto Alterado",
                    "Nova Descrição",
                    BigDecimal.valueOf(200.0),
                    20,
                    new CategoriaResponse(categoriaId.toString(), "Categoria Teste")
            );

            // Simula sucesso no AlterProdutoUseCase
            when(alterProdutoUseCase.execute(any())).thenReturn(new Produto(
                    produtoId,
                    "Produto Alterado",
                    "Nova Descrição",
                    BigDecimal.valueOf(200.0),
                    20,
                    new Categoria(categoriaId, "Categoria Teste")
            ));

            mockMvc.perform(patch("/produtos/{id}", produtoId)
                            .contentType("application/json")
                            .content("{\"nome\": \"Produto Alterado\", \"descricao\": \"Nova Descrição\", \"preco\": 200.0, \"quantidadeEstoque\": 20}"))
                    .andExpect(status().isOk());
        }

        @Test
        public void testAlterProduto_EntityNotFoundException() throws Exception {
            UUID produtoId = UUID.randomUUID();

            // Simula que o AlterProdutoUseCase lança uma EntityNotFoundException
            when(alterProdutoUseCase.execute(any())).thenThrow(new EntityNotFoundException("Produto não encontrado"));

            mockMvc.perform(patch("/produtos/{id}", produtoId)
                            .contentType("application/json")
                            .content("{\"nome\": \"Produto Alterado\", \"descricao\": \"Nova Descrição\", \"preco\": 200.0, \"quantidadeEstoque\": 20}"))
                    .andExpect(status().isNotFound()) // Status HTTP 404
                    .andExpect(content().string("Produto não encontrado"));
        }

        // Teste de alteração de produto com RuntimeException
        @Test
        public void testAlterProduto_RuntimeException() throws Exception {
            UUID produtoId = UUID.randomUUID();

            // Simula que o AlterProdutoUseCase lança uma RuntimeException
            when(alterProdutoUseCase.execute(any())).thenThrow(new RuntimeException("Erro inesperado ao alterar produto"));

            mockMvc.perform(patch("/produtos/{id}", produtoId)
                            .contentType("application/json")
                            .content("{\"nome\": \"Produto Alterado\", \"descricao\": \"Nova Descrição\", \"preco\": 200.0, \"quantidadeEstoque\": 20}"))
                    .andExpect(status().isInternalServerError()) // Status HTTP 500
                    .andExpect(content().string("Erro inesperado ao alterar o produto."));
        }
    }
    @Nested
    class testeBaixaProdutos {

        @Test
        public void testBaixaEstoqueProduto_Success() throws Exception {
            UUID produtoId = UUID.randomUUID();
            Integer quantidade = 5;
            UUID categoriaId = UUID.randomUUID();

            Produto produto = new Produto(
                    produtoId,
                    "Produto Teste",
                    "Descrição Teste",
                    BigDecimal.valueOf(100.0),
                    10, // Quantidade inicial no estoque
                    new Categoria(categoriaId, "Categoria Teste")
            );

            // Simula sucesso no BaixaEstoqueProdutoUseCase
            when(baixaEstoqueProdutoUseCase.execute(produtoId, quantidade)).thenReturn(new Produto(
                    produtoId,
                    "Produto Teste",
                    "Descrição Teste",
                    BigDecimal.valueOf(100.0),
                    10 - quantidade, // Decrementa a quantidade
                    new Categoria(categoriaId, "Categoria Teste")
            ));

            mockMvc.perform(patch("/produtos/{id}/baixa-estoque", produtoId)
                            .param("quantidade", quantidade.toString()))
                    .andExpect(status().isOk());
        }

        @Test
        public void testBaixaEstoqueProduto_EntityNotFoundException() throws Exception {
            UUID produtoId = UUID.randomUUID();

            // Simula que o BaixaEstoqueProdutoUseCase lança uma EntityNotFoundException
            when(baixaEstoqueProdutoUseCase.execute(produtoId, 10)).thenThrow(new EntityNotFoundException("Produto não encontrado"));

            mockMvc.perform(patch("/produtos/{id}/baixa-estoque", produtoId)
                            .param("quantidade", "10"))
                    .andExpect(status().isNotFound()) // Status HTTP 404
                    .andExpect(content().string("Produto não encontrado"));
        }
    }

    @Nested
    class testeEstornoProdutos {

        @Test
        public void testEstornarEstoque_Success() throws Exception {
            UUID produtoId = UUID.randomUUID();
            Integer quantidade = 3;
            UUID categoriaId = UUID.randomUUID();

            Produto produto = new Produto(
                    produtoId,
                    "Produto Teste",
                    "Descrição Teste",
                    BigDecimal.valueOf(100.0),
                    10, // Quantidade no estoque antes do estorno
                    new Categoria(categoriaId, "Categoria Teste")
            );

            // Simula sucesso no EstornoEstoqueUseCase
            when(estornoEstoqueUseCase.execute(produtoId, quantidade)).thenReturn(new Produto(
                    produtoId,
                    "Produto Teste",
                    "Descrição Teste",
                    BigDecimal.valueOf(100.0),
                    10 + quantidade, // Aumenta a quantidade do estoque
                    new Categoria(categoriaId, "Categoria Teste")
            ));

            mockMvc.perform(patch("/produtos/{id}/estorno-estoque", produtoId)
                            .param("quantidade", quantidade.toString()))
                    .andExpect(status().isOk());
        }

        @Test
        public void testEstornoEstoqueProduto_EntityNotFoundException() throws Exception {
            UUID produtoId = UUID.randomUUID();

            // Simula que o EstornoEstoqueUseCase lança uma EntityNotFoundException
            when(estornoEstoqueUseCase.execute(produtoId, 10)).thenThrow(new EntityNotFoundException("Produto não encontrado"));

            mockMvc.perform(patch("/produtos/{id}/estorno-estoque", produtoId)
                            .param("quantidade", "10"))
                    .andExpect(status().isNotFound()) // Status HTTP 404
                    .andExpect(content().string("Produto não encontrado"));
        }
    }

    @Test
    public void testSearchProdutos_Success() throws Exception {
        UUID categoriaId = UUID.randomUUID();
        List<Produto> produtos = List.of(
                new Produto(UUID.randomUUID(), "Produto Teste", "Descrição Teste", BigDecimal.valueOf(100.0), 10, new Categoria(categoriaId, "Categoria Teste"))
        );

        // Simula sucesso no SearchProdutosUseCase
        when(searchProdutosUseCase.execute(anyString(), anyString())).thenReturn(produtos);

        mockMvc.perform(get("/produtos/search")
                        .param("nome", "Produto Teste")
                        .param("descricao", "Descrição Teste"))
                .andExpect(status().isOk());
    }

}