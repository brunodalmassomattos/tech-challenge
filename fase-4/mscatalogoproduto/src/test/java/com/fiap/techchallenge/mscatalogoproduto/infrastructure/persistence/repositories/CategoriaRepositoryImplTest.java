package com.fiap.techchallenge.mscatalogoproduto.infrastructure.persistence.repositories;

import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Categoria;
import com.fiap.techchallenge.mscatalogoproduto.infrastructure.mappers.CategoriaMapper;
import com.fiap.techchallenge.mscatalogoproduto.infrastructure.persistence.entities.CategoriaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoriaRepositoryImplTest {

    @Mock
    private DataCategoriaRepository dataCategoriaRepository;

    @InjectMocks
    private CategoriaRepositoryImpl categoriaRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById_Success() {
        UUID categoriaId = UUID.randomUUID();
        CategoriaEntity categoriaEntity = new CategoriaEntity(categoriaId, "Categoria Teste");
        when(dataCategoriaRepository.findById(categoriaId)).thenReturn(Optional.of(categoriaEntity));

        Optional<Categoria> result = categoriaRepository.findById(categoriaId);

        assertTrue(result.isPresent());
        assertEquals(categoriaId, result.get().getId());
        assertEquals("Categoria Teste", result.get().getDescricao());
        verify(dataCategoriaRepository, times(1)).findById(categoriaId);
    }

    @Test
    void testFindById_NotFound() {
        UUID categoriaId = UUID.randomUUID();
        when(dataCategoriaRepository.findById(categoriaId)).thenReturn(Optional.empty());

        Optional<Categoria> result = categoriaRepository.findById(categoriaId);

        assertFalse(result.isPresent());
        verify(dataCategoriaRepository, times(1)).findById(categoriaId);
    }

    @Test
    void testFindAll_Success() {
        UUID categoriaId1 = UUID.randomUUID();
        UUID categoriaId2 = UUID.randomUUID();
        List<CategoriaEntity> categoriaEntities = List.of(
                new CategoriaEntity(categoriaId1, "Categoria 1"),
                new CategoriaEntity(categoriaId2, "Categoria 2")
        );
        when(dataCategoriaRepository.findAll()).thenReturn(categoriaEntities);

        List<Categoria> result = categoriaRepository.findAll();

        assertEquals(2, result.size());
        assertEquals("Categoria 1", result.get(0).getDescricao());
        assertEquals("Categoria 2", result.get(1).getDescricao());
        verify(dataCategoriaRepository, times(1)).findAll();
    }

    @Test
    void testSave_Success() {
        UUID categoriaId = UUID.randomUUID();
        Categoria categoria = new Categoria(categoriaId, "Categoria Teste");
        CategoriaEntity categoriaEntity = CategoriaMapper.toEntity(categoria);
        when(dataCategoriaRepository.save(categoriaEntity)).thenReturn(categoriaEntity);

        Categoria result = categoriaRepository.save(categoria);

        assertNotNull(result);
        assertEquals(categoriaId, result.getId());
        assertEquals("Categoria Teste", result.getDescricao());
        verify(dataCategoriaRepository, times(1)).save(categoriaEntity);
    }

    @Test
    void testExistsByDescricao_Success() {
        String descricao = "Categoria Teste";
        when(dataCategoriaRepository.existsByDescricao(descricao)).thenReturn(true);

        boolean exists = categoriaRepository.existsByDescricao(descricao);

        assertTrue(exists);
        verify(dataCategoriaRepository, times(1)).existsByDescricao(descricao);
    }

    @Test
    void testDelete_Success() {
        UUID categoriaId = UUID.randomUUID();
        Categoria categoria = new Categoria(categoriaId, "Categoria Teste");
        CategoriaEntity categoriaEntity = CategoriaMapper.toEntity(categoria);

        doNothing().when(dataCategoriaRepository).delete(categoriaEntity);

        categoriaRepository.delete(categoria);

        verify(dataCategoriaRepository, times(1)).delete(categoriaEntity);
    }
}