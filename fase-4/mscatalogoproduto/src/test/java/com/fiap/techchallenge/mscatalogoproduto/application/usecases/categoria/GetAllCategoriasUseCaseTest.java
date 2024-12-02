package com.fiap.techchallenge.mscatalogoproduto.application.usecases.categoria;

import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Categoria;
import com.fiap.techchallenge.mscatalogoproduto.domain.repositories.CategoriaRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetAllCategoriasUseCaseTest {

    private final CategoriaRepository categoriaRepository = mock(CategoriaRepository.class);
    private final GetAllCategoriasUseCase buscarCategoriasUseCase = new GetAllCategoriasUseCase(categoriaRepository);

    @Test
    void deveRetornarTodasAsCategorias() {
        Categoria categoria1 = new Categoria();
        categoria1.setId(UUID.randomUUID());
        categoria1.setDescricao("Eletrônicos");

        Categoria categoria2 = new Categoria();
        categoria2.setId(UUID.randomUUID());
        categoria2.setDescricao("Eletrodomésticos");

        when(categoriaRepository.findAll()).thenReturn(List.of(categoria1, categoria2));

        List<Categoria> categorias = buscarCategoriasUseCase.execute();

        assertEquals(2, categorias.size());
        assertEquals("Eletrônicos", categorias.get(0).getDescricao());
        assertEquals("Eletrodomésticos", categorias.get(1).getDescricao());
    }

}