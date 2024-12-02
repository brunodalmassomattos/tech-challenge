package com.fiap.techchallenge.mscatalogoproduto.application.usecases.categoria;

import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Categoria;
import com.fiap.techchallenge.mscatalogoproduto.domain.repositories.CategoriaRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GetCategoriaUseCaseTest {

    private final CategoriaRepository repository = mock(CategoriaRepository.class);
    private final GetCategoriaUseCase getCategoriaUseCase = new GetCategoriaUseCase(repository);

    @Test
    void deveRetornarCategoriaQuandoIdExistir() {
        UUID id = UUID.randomUUID();
        Categoria categoria = new Categoria();
        categoria.setId(id);
        categoria.setDescricao("Eletrônicos");

        when(repository.findById(id)).thenReturn(Optional.of(categoria));

        Categoria categoriaRetornada = getCategoriaUseCase.execute(id);

        assertNotNull(categoriaRetornada);
        assertEquals(id, categoriaRetornada.getId());
        assertEquals("Eletrônicos", categoriaRetornada.getDescricao());
        verify(repository, times(1)).findById(id);
    }

    @Test
    void deveLancarExcecaoQuandoIdNaoExistir() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> getCategoriaUseCase.execute(id)
        );

        assertEquals("Categoria não localizado para o id: " + id, exception.getMessage());
        verify(repository, times(1)).findById(id);
    }

}