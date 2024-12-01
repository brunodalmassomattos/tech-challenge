package com.fiap.techchallenge.mscatalogoproduto.application.usecases.categoria;

import com.fiap.techchallenge.mscatalogoproduto.application.usecases.execption.CategoriaException;
import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Categoria;
import com.fiap.techchallenge.mscatalogoproduto.domain.repositories.CategoriaRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateCategoriaUseCaseTest {

    private final CategoriaRepository categoriaRepository = mock(CategoriaRepository.class);
    private final CreateCategoriaUseCase criarCategoriaUseCase = new CreateCategoriaUseCase(categoriaRepository);

    @Test
    void deveCriarCategoriaComSucesso() {
        String descricao = "Eletrônicos";

        when(categoriaRepository.existsByDescricao(descricao)).thenReturn(false);
        when(categoriaRepository.save(any(Categoria.class)))
                .thenAnswer(invocation -> {
                    Categoria categoria = invocation.getArgument(0);
                    categoria.setId(java.util.UUID.randomUUID());
                    return categoria;
                });

        Categoria categoriaCriada = criarCategoriaUseCase.execute(descricao);

        assertNotNull(categoriaCriada.getId());
        assertEquals(descricao.toLowerCase(), categoriaCriada.getDescricao());
        verify(categoriaRepository, times(1)).save(any(Categoria.class));
    }

    @Test
    void deveLancarExcecaoQuandoCategoriaJaExiste() {
        String descricao = "Eletrônicos";

        when(categoriaRepository.existsByDescricao(descricao)).thenReturn(true);

        CategoriaException exception = assertThrows(
                CategoriaException.class,
                () -> criarCategoriaUseCase.execute(descricao)
        );

        assertEquals("Já existe uma categoria com esta descrição.", exception.getMessage());
        verify(categoriaRepository, never()).save(any(Categoria.class));
    }
}