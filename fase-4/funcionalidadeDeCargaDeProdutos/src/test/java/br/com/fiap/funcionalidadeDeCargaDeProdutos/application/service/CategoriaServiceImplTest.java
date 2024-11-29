package br.com.fiap.funcionalidadeDeCargaDeProdutos.application.service;

import br.com.fiap.funcionalidadeDeCargaDeProdutos.application.dto.CategoriaDTO;
import br.com.fiap.funcionalidadeDeCargaDeProdutos.application.mapper.CategoriaMapper;
import br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.entity.Categoria;
import br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.exception.ResourceNotFoundException;
import br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.repository.CategoriaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceImplTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private CategoriaMapper categoriaMapper;

    @InjectMocks
    private CategoriaServiceImpl categoriaService;

    private Categoria categoria;
    private CategoriaDTO categoriaDTO;
    private UUID categoriaId;

    @BeforeEach
    void setUp() {
        categoriaId = UUID.randomUUID();
        categoria = new Categoria();
        categoria.setId(categoriaId);
        categoria.setDescricao("Eletrônicos");

        categoriaDTO = new CategoriaDTO(categoriaId, "Eletrônicos");
    }

    @Test
    void deveCriarCategoriaComSucesso() {
        when(categoriaMapper.toEntity(any())).thenReturn(categoria);
        when(categoriaRepository.save(any())).thenReturn(categoria);
        when(categoriaMapper.toDto(any())).thenReturn(categoriaDTO);

        CategoriaDTO resultado = categoriaService.createCategoria(categoriaDTO);

        assertThat(resultado).isNotNull();
        assertThat(resultado.descricao()).isEqualTo("Eletrônicos");
        verify(categoriaRepository, times(1)).save(any());
    }

    @Test
    void deveRetornarCategoriaPorId() {
        when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.of(categoria));
        when(categoriaMapper.toDto(any())).thenReturn(categoriaDTO);

        CategoriaDTO resultado = categoriaService.getCategoriaById(categoriaId);

        assertThat(resultado).isNotNull();
        assertThat(resultado.id()).isEqualTo(categoriaId);
        verify(categoriaRepository, times(1)).findById(categoriaId);
    }

    @Test
    void deveLancarExcecaoQuandoCategoriaNaoEncontradaPorId() {
        when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoriaService.getCategoriaById(categoriaId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Categoria não encontrada com id");

        verify(categoriaRepository, times(1)).findById(categoriaId);
    }

    @Test
    void deveAtualizarCategoriaComSucesso() {
        CategoriaDTO categoriaAtualizadaDTO = new CategoriaDTO(categoriaId, "Informática");
        Categoria categoriaAtualizada = new Categoria();
        categoriaAtualizada.setId(categoriaId);
        categoriaAtualizada.setDescricao("Informática");

        when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.of(categoria));
        when(categoriaRepository.save(any())).thenReturn(categoriaAtualizada);
        when(categoriaMapper.toDto(any())).thenReturn(categoriaAtualizadaDTO);

        CategoriaDTO resultado = categoriaService.updateCategoria(categoriaId, categoriaAtualizadaDTO);

        assertThat(resultado).isNotNull();
        assertThat(resultado.descricao()).isEqualTo("Informática");
        verify(categoriaRepository, times(1)).save(any());
    }

    @Test
    void deveExcluirCategoriaComSucesso() {
        when(categoriaRepository.existsById(categoriaId)).thenReturn(true);

        categoriaService.deleteCategoria(categoriaId);

        verify(categoriaRepository, times(1)).deleteById(categoriaId);
    }

    @Test
    void deveLancarExcecaoAoExcluirCategoriaInexistente() {
        when(categoriaRepository.existsById(categoriaId)).thenReturn(false);

        assertThatThrownBy(() -> categoriaService.deleteCategoria(categoriaId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Categoria não encontrada com id");

        verify(categoriaRepository, never()).deleteById(any());
    }

    @Test
    void whenListarTodasCategoriasTest() {
        List<Categoria> categorias = Arrays.asList(categoria);
        List<CategoriaDTO> categoriasDTO = Arrays.asList(categoriaDTO);

        when(categoriaRepository.findAll()).thenReturn(categorias);
        when(categoriaMapper.toDto(any())).thenReturn(categoriaDTO);

        List<CategoriaDTO> resultado = categoriaService.getAllCategorias();

        assertThat(resultado).isNotEmpty();
        assertThat(resultado.size()).isEqualTo(1);
        verify(categoriaRepository, times(1)).findAll();
    }
}
