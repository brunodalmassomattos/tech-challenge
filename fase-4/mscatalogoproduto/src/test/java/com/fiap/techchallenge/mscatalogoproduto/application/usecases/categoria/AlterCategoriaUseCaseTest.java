package com.fiap.techchallenge.mscatalogoproduto.application.usecases.categoria;

import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Categoria;
import com.fiap.techchallenge.mscatalogoproduto.domain.repositories.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AlterCategoriaUseCaseTest {

    private final CategoriaRepository repository = mock(CategoriaRepository.class);
    private final AlterCategoriaUseCase alterCategoriaUseCase = new AlterCategoriaUseCase(repository);

    @Test
    void deveAlterarCategoriaComSucesso() {
        UUID id = UUID.randomUUID();
        Categoria categoriaAtualizada = new Categoria(id, "Nova Descrição");

        Categoria categoriaSalva = new Categoria();
        categoriaSalva.setId(id);
        categoriaSalva.setDescricao("Descrição Antiga");

        when(repository.existsByDescricao("Nova Descrição")).thenReturn(false);
        when(repository.findById(id)).thenReturn(Optional.of(categoriaSalva));
        when(repository.save(any(Categoria.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Categoria resultado = alterCategoriaUseCase.execute(categoriaAtualizada);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("Nova Descrição", resultado.getDescricao());
        verify(repository, times(1)).existsByDescricao("Nova Descrição");
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(any(Categoria.class));
    }

    @Test
    void deveLancarExcecaoQuandoDescricaoJaExiste() {
        UUID id = UUID.randomUUID();
        Categoria categoriaAtualizada = new Categoria(id, "Descrição Existente");

        when(repository.existsByDescricao("Descrição Existente")).thenReturn(true);

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> alterCategoriaUseCase.execute(categoriaAtualizada)
        );

        assertEquals("Já existe uma categoria com esta descrição.", exception.getMessage());
        verify(repository, never()).findById(any());
        verify(repository, never()).save(any());
    }

    @Test
    void deveLancarExcecaoQuandoIdNaoExiste() {
        UUID id = UUID.randomUUID();
        Categoria categoriaAtualizada = new Categoria(id, "Nova Descrição");

        when(repository.existsByDescricao("Nova Descrição")).thenReturn(false);
        when(repository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> alterCategoriaUseCase.execute(categoriaAtualizada)
        );

        assertEquals("Categoria  não localizado para o id: " + id, exception.getMessage());
        verify(repository, times(1)).findById(id);
        verify(repository, never()).save(any());
    }

    @Test
    void deveManterDescricaoAntigaQuandoNovaDescricaoForNulaOuVazia() {
        UUID id = UUID.randomUUID();
        Categoria categoriaAtualizada = new Categoria(id, "");

        Categoria categoriaSalva = new Categoria();
        categoriaSalva.setId(id);
        categoriaSalva.setDescricao("Descrição Antiga");

        when(repository.existsByDescricao("")).thenReturn(false);
        when(repository.findById(id)).thenReturn(Optional.of(categoriaSalva));
        when(repository.save(any(Categoria.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Categoria resultado = alterCategoriaUseCase.execute(categoriaAtualizada);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("Descrição Antiga", resultado.getDescricao());
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(any(Categoria.class));
    }
}
