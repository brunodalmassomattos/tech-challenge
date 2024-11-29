package br.com.fiap.funcionalidadeDeCargaDeProdutos.application.service;

import br.com.fiap.funcionalidadeDeCargaDeProdutos.application.dto.ProdutoDTO;
import br.com.fiap.funcionalidadeDeCargaDeProdutos.application.mapper.ProdutoMapper;
import br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.entity.Categoria;
import br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.entity.Produto;
import br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.exception.ResourceNotFoundException;
import br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.repository.CategoriaRepository;
import br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.repository.ProdutoRepository;
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
class ProdutoServiceImplTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private ProdutoMapper produtoMapper;

    @InjectMocks
    private ProdutoServiceImpl produtoService;

    private Produto produto;
    private ProdutoDTO produtoDTO;
    private Categoria categoria;
    private UUID produtoId;
    private UUID categoriaId;

    @BeforeEach
    void setUp() {
        categoriaId = UUID.randomUUID();
        produtoId = UUID.randomUUID();

        categoria = new Categoria();
        categoria.setId(categoriaId);
        categoria.setDescricao("Eletrônicos");

        produto = new Produto();
        produto.setId(produtoId);
        produto.setNome("Smartphone XYZ");
        produto.setDescricao("Um smartphone de última geração.");
        produto.setPreco(2999.99);
        produto.setQtdEstoque(50);
        produto.setCategoria(categoria);

        produtoDTO = new ProdutoDTO(produtoId, "Smartphone XYZ", "Um smartphone de última geração.", 2999.99, 50, categoriaId);
    }

    @Test
    void deveCriarProdutoComSucesso() {
        when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.of(categoria));
        when(produtoMapper.toEntity(any(), any())).thenReturn(produto);
        when(produtoRepository.save(any())).thenReturn(produto);
        when(produtoMapper.toDto(any())).thenReturn(produtoDTO);

        ProdutoDTO resultado = produtoService.createProduto(produtoDTO);

        assertThat(resultado).isNotNull();
        assertThat(resultado.nome()).isEqualTo("Smartphone XYZ");
        verify(produtoRepository, times(1)).save(any());
    }

    @Test
    void deveLancarExcecaoQuandoCategoriaNaoEncontradaAoCriarProduto() {
        when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> produtoService.createProduto(produtoDTO))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Categoria não encontrada com id");

        verify(produtoRepository, never()).save(any());
    }

    @Test
    void deveRetornarProdutoPorId() {
        when(produtoRepository.findById(produtoId)).thenReturn(Optional.of(produto));
        when(produtoMapper.toDto(any())).thenReturn(produtoDTO);

        ProdutoDTO resultado = produtoService.getProdutoById(produtoId);

        assertThat(resultado).isNotNull();
        assertThat(resultado.id()).isEqualTo(produtoId);
        verify(produtoRepository, times(1)).findById(produtoId);
    }

    @Test
    void deveLancarExcecaoQuandoProdutoNaoEncontradoPorId() {
        when(produtoRepository.findById(produtoId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> produtoService.getProdutoById(produtoId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Produto não encontrado com id");

        verify(produtoRepository, times(1)).findById(produtoId);
    }

    @Test
    void deveAtualizarProdutoComSucesso() {
        ProdutoDTO produtoAtualizadoDTO = new ProdutoDTO(produtoId, "Smartphone XYZ Pro", "Versão atualizada.", 3299.99, 40, categoriaId);
        Produto produtoAtualizado = new Produto();
        produtoAtualizado.setId(produtoId);
        produtoAtualizado.setNome("Smartphone XYZ Pro");
        produtoAtualizado.setDescricao("Versão atualizada.");
        produtoAtualizado.setPreco(3299.99);
        produtoAtualizado.setQtdEstoque(40);
        produtoAtualizado.setCategoria(categoria);

        when(produtoRepository.findById(produtoId)).thenReturn(Optional.of(produto));
        when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.of(categoria));
        when(produtoRepository.save(any())).thenReturn(produtoAtualizado);
        when(produtoMapper.toDto(any())).thenReturn(produtoAtualizadoDTO);

        ProdutoDTO resultado = produtoService.updateProduto(produtoId, produtoAtualizadoDTO);

        assertThat(resultado).isNotNull();
        assertThat(resultado.nome()).isEqualTo("Smartphone XYZ Pro");
        verify(produtoRepository, times(1)).save(any());
    }

    @Test
    void deveExcluirProdutoComSucesso() {
        when(produtoRepository.existsById(produtoId)).thenReturn(true);

        produtoService.deleteProduto(produtoId);

        verify(produtoRepository, times(1)).deleteById(produtoId);
    }

    @Test
    void deveLancarExcecaoAoExcluirProdutoInexistente() {
        when(produtoRepository.existsById(produtoId)).thenReturn(false);

        assertThatThrownBy(() -> produtoService.deleteProduto(produtoId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Produto não encontrado com id");

        verify(produtoRepository, never()).deleteById(any());
    }

    @Test
    void deveListarTodosProdutos() {
        List<Produto> produtos = Arrays.asList(produto);
        List<ProdutoDTO> produtosDTO = Arrays.asList(produtoDTO);

        when(produtoRepository.findAll()).thenReturn(produtos);
        when(produtoMapper.toDto(any())).thenReturn(produtoDTO);

        List<ProdutoDTO> resultado = produtoService.getAllProdutos();

        assertThat(resultado).isNotEmpty();
        assertThat(resultado.size()).isEqualTo(1);
        verify(produtoRepository, times(1)).findAll();
    }
}

