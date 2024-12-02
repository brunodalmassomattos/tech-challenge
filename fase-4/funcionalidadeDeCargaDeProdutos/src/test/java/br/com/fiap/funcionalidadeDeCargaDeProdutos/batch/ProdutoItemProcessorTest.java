package br.com.fiap.funcionalidadeDeCargaDeProdutos.batch;

import br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.entity.Categoria;
import br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.entity.Produto;
import br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.repository.CategoriaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

public class ProdutoItemProcessorTest {

    private CategoriaRepository categoriaRepository;
    private ProdutoItemProcessor processor;

    @BeforeEach
    public void setUp() {
        categoriaRepository = Mockito.mock(CategoriaRepository.class);
        processor = new ProdutoItemProcessor(categoriaRepository);
    }

    @Test
    public void whenProcessValidProdutoTest() throws Exception {
        ProdutoInput input = new ProdutoInput();
        input.setNome("Produto Teste");
        input.setDescricao("Descrição do produto teste");
        input.setPreco(new BigDecimal("100.00"));
        input.setQtdEstoque(10);
        input.setCategoriaDescricao("Categoria Teste");

        Categoria categoria = new Categoria();
        categoria.setId(UUID.randomUUID());
        categoria.setDescricao("Categoria Teste");

        Mockito.when(categoriaRepository.findByDescricao(anyString()))
                .thenReturn(Optional.of(categoria));

        Produto produto = processor.process(input);

        assertNotNull(produto);
        assertEquals(input.getNome(), produto.getNome());
        assertEquals(input.getDescricao(), produto.getDescricao());
        assertEquals(input.getPreco(), produto.getPreco());
        assertEquals(input.getQtdEstoque(), produto.getQtdEstoque());
        assertEquals(categoria, produto.getCategoria());
    }

    @Test
    public void whenProcessInvalidPrecoTest() {
        ProdutoInput input = new ProdutoInput();
        input.setNome("Produto Teste");
        input.setDescricao("Descrição do produto teste");
        input.setPreco(new BigDecimal("-100.00")); // Preço negativo
        input.setQtdEstoque(10);
        input.setCategoriaDescricao("Categoria Teste");

        Categoria categoria = new Categoria();
        categoria.setId(UUID.randomUUID());
        categoria.setDescricao("Categoria Teste");

        Mockito.when(categoriaRepository.findByDescricao(anyString()))
                .thenReturn(Optional.of(categoria));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            processor.process(input);
        });

        assertEquals("Preço negativo: -100.00", exception.getMessage());
    }

    @Test
    public void whenProcessCategoriaNaoExistenteTest() throws Exception {
        ProdutoInput input = new ProdutoInput();
        input.setNome("Produto Teste");
        input.setDescricao("Descrição do produto teste");
        input.setPreco(new BigDecimal("100.00"));
        input.setQtdEstoque(10);
        input.setCategoriaDescricao("Nova Categoria");

        Mockito.when(categoriaRepository.findByDescricao(anyString()))
                .thenReturn(Optional.empty());

        Mockito.when(categoriaRepository.save(Mockito.any(Categoria.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Produto produto = processor.process(input);

        assertNotNull(produto);
        assertEquals("Nova Categoria", produto.getCategoria().getDescricao());
    }
}