package br.com.fiap.funcionalidadeDeCargaDeProdutos.batch;

import br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.entity.Categoria;
import br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.entity.Produto;
import br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.repository.CategoriaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.math.BigDecimal;

public class ProdutoItemProcessor implements ItemProcessor<ProdutoInput, Produto> {

    private static final Logger logger = LoggerFactory.getLogger(ProdutoItemProcessor.class);

    private final CategoriaRepository categoriaRepository;

    public ProdutoItemProcessor(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public Produto process(ProdutoInput produtoInput) throws Exception {
        try {
            Produto produto = new Produto();

            produto.setNome(produtoInput.getNome());
            produto.setDescricao(produtoInput.getDescricao());
            produto.setPreco(produtoInput.getPreco());
            produto.setQtdEstoque(produtoInput.getQtdEstoque());

            Categoria categoria = categoriaRepository.findByDescricao(produtoInput.getCategoriaDescricao())
                    .orElseGet(() -> {
                        Categoria novaCategoria = new Categoria();
                        novaCategoria.setDescricao(produtoInput.getCategoriaDescricao());
                        return categoriaRepository.save(novaCategoria);
                    });
            produto.setCategoria(categoria);

            if (produto.getPreco().compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Preço negativo: " + produto.getPreco());
            }
            if (produto.getQtdEstoque() < 0) {
                throw new IllegalArgumentException("Estoque negativo: " + produto.getQtdEstoque());
            }

            return produto;
        } catch (Exception e) {
            logger.error("Erro ao processar o produto: " + produtoInput, e);
            throw e;
        }
    }
}