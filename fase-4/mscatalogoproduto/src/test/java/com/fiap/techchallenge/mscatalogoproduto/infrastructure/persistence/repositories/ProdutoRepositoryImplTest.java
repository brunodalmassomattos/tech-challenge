package com.fiap.techchallenge.mscatalogoproduto.infrastructure.persistence.repositories;


import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Categoria;
import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Produto;
import com.fiap.techchallenge.mscatalogoproduto.infrastructure.mappers.ProdutoMapper;
import com.fiap.techchallenge.mscatalogoproduto.infrastructure.persistence.entities.CategoriaEntity;
import com.fiap.techchallenge.mscatalogoproduto.infrastructure.persistence.entities.ProdutoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProdutoRepositoryImplTest {

    @Mock
    private DataProdutoRepository dataProdutoRepository;

    @InjectMocks
    private ProdutoRepositoryImpl produtoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById_Success() {
        UUID produtoId = UUID.randomUUID();
        ProdutoEntity produtoEntity = new ProdutoEntity(
                produtoId, "Produto Teste", "Descrição Teste", BigDecimal.valueOf(100.0), 10,
                new CategoriaEntity(UUID.randomUUID(), "Categoria Teste"));
        when(dataProdutoRepository.findById(produtoId)).thenReturn(Optional.of(produtoEntity));

        Optional<Produto> result = produtoRepository.findById(produtoId);

        assertTrue(result.isPresent());
        assertEquals(produtoId, result.get().getId());
        assertEquals("Produto Teste", result.get().getNome());
        verify(dataProdutoRepository, times(1)).findById(produtoId);
    }

    @Test
    void testFindById_NotFound() {
        UUID produtoId = UUID.randomUUID();
        when(dataProdutoRepository.findById(produtoId)).thenReturn(Optional.empty());

        Optional<Produto> result = produtoRepository.findById(produtoId);

        assertFalse(result.isPresent());
        verify(dataProdutoRepository, times(1)).findById(produtoId);
    }

    @Test
    void testFindAll_Success() {
        List<ProdutoEntity> produtoEntities = List.of(
                new ProdutoEntity(UUID.randomUUID(), "Produto 1", "Descrição 1", BigDecimal.valueOf(100.0), 10,
                        new CategoriaEntity(UUID.randomUUID(), "Categoria Teste")),

                new ProdutoEntity(UUID.randomUUID(), "Produto 2", "Descrição 2", BigDecimal.valueOf(200.0), 20,
                        new CategoriaEntity(UUID.randomUUID(), "Categoria Teste"))
        );
        when(dataProdutoRepository.findAll()).thenReturn(produtoEntities);

        List<Produto> result = produtoRepository.findAll();

        assertEquals(2, result.size());
        assertEquals("Produto 1", result.get(0).getNome());
        assertEquals("Produto 2", result.get(1).getNome());
        verify(dataProdutoRepository, times(1)).findAll();
    }

    @Test
    void testFindByNameOrDescription_Success() {
        String nome = "Teste";
        String descricao = "Descrição";

        List<ProdutoEntity> produtoEntities = List.of(
                new ProdutoEntity(UUID.randomUUID(), "Produto Teste", "Descrição Teste", BigDecimal.valueOf(100.0), 10,
                        new CategoriaEntity(UUID.randomUUID(), "Categoria Teste" ))
        );

        when(dataProdutoRepository.findByNomeContainingOrDescricaoContainingIgnoreCase(nome, descricao))
                .thenReturn(produtoEntities);

        List<Produto> result = produtoRepository.findByNameOrDescription(nome, descricao);

        assertEquals(1, result.size());
        assertEquals("Produto Teste", result.get(0).getNome());
        verify(dataProdutoRepository, times(1))
                .findByNomeContainingOrDescricaoContainingIgnoreCase(nome, descricao);
    }

    @Test
    void testSave_Success() {
        UUID produtoId = UUID.randomUUID();
        Produto produto = new Produto(
                produtoId, "Produto Teste", "Descrição Teste", BigDecimal.valueOf(100.0), 10,
                new Categoria(UUID.randomUUID(), "Categoria Teste"));
        ProdutoEntity produtoEntity = ProdutoMapper.toEntity(produto);
        when(dataProdutoRepository.save(produtoEntity)).thenReturn(produtoEntity);

        Produto result = produtoRepository.save(produto);

        assertNotNull(result);
        assertEquals(produtoId, result.getId());
        assertEquals("Produto Teste".toLowerCase(), result.getNome());
        verify(dataProdutoRepository, times(1)).save(produtoEntity);
    }

    @Test
    void testDelete_Success() {
        UUID produtoId = UUID.randomUUID();
        Produto produto = new Produto(
                produtoId, "Produto Teste", "Descrição Teste", BigDecimal.valueOf(100.0), 10,
                new Categoria(UUID.randomUUID(), "Categoria Teste"));
        ProdutoEntity produtoEntity = ProdutoMapper.toEntity(produto);

        doNothing().when(dataProdutoRepository).delete(produtoEntity);

        produtoRepository.delete(produto);

        verify(dataProdutoRepository, times(1)).delete(produtoEntity);
    }
}