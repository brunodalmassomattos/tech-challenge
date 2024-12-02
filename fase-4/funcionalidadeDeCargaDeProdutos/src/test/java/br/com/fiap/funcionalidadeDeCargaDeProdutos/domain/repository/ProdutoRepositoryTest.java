package br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.repository;

import br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.entity.Categoria;
import br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.entity.Produto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ProdutoRepositoryTest {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Test
    public void whenSaveAndFindProdutoTest() {
        Categoria categoria = new Categoria();
        categoria.setDescricao("Categoria Teste");
        categoria = categoriaRepository.save(categoria);

        Produto produto = new Produto();
        produto.setNome("Produto Teste");
        produto.setDescricao("Descrição do produto teste");
        produto.setPreco(new BigDecimal("100.00"));
        produto.setQtdEstoque(10);
        produto.setCategoria(categoria);

        produto = produtoRepository.save(produto);

        Optional<Produto> found = produtoRepository.findById(produto.getId());

        assertTrue(found.isPresent());
        assertEquals(produto.getNome(), found.get().getNome());
    }

    @Test
    public void whenFindByNomeTest() {
        Categoria categoria = new Categoria();
        categoria.setDescricao("Categoria Teste");
        categoria = categoriaRepository.save(categoria);

        Produto produto = new Produto();
        produto.setNome("Produto Único");
        produto.setDescricao("Descrição do produto único");
        produto.setPreco(new BigDecimal("200.00"));
        produto.setQtdEstoque(5);
        produto.setCategoria(categoria);

        produtoRepository.save(produto);

        Optional<Produto> found = produtoRepository.findByNome("Produto Único");

        assertTrue(found.isPresent());
        assertEquals(produto.getDescricao(), found.get().getDescricao());
    }
}

