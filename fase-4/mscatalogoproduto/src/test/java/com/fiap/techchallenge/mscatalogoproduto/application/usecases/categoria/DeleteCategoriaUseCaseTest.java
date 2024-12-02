package com.fiap.techchallenge.mscatalogoproduto.application.usecases.categoria;

import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Categoria;
import com.fiap.techchallenge.mscatalogoproduto.domain.repositories.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DeleteCategoriaUseCaseTest {

    private final CategoriaRepository repository = mock(CategoriaRepository.class);
    private final DeleteCategoriaUseCase deleteCategoriaUseCase = new DeleteCategoriaUseCase(repository);

    @Test
    void deveDeletarCategoriaQuandoIdExistir() {
        UUID id = UUID.randomUUID();
        Categoria categoria = new Categoria();
        categoria.setId(id);
        categoria.setDescricao("Eletrônicos");

        when(repository.findById(id)).thenReturn(Optional.of(categoria));

        deleteCategoriaUseCase.execute(id);

        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).delete(categoria);
    }

    @Test
    void deveLancarExcecaoQuandoIdNaoExistir() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> deleteCategoriaUseCase.execute(id)
        );

        assertEquals("Categoria não encontrado com ID: " + id, exception.getMessage());
        verify(repository, times(1)).findById(id);
        verify(repository, never()).delete(any());
    }
}