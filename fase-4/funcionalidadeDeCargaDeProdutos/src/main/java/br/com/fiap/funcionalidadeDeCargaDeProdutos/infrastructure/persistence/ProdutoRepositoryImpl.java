package br.com.fiap.funcionalidadeDeCargaDeProdutos.infrastructure.persistence;

import br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.entity.Produto;
import br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProdutoRepositoryImpl implements ProdutoRepository {

    private final JpaProdutoRepository jpaProdutoRepository;

    @Override
    public Produto save(Produto produto) {
        return jpaProdutoRepository.save(produto);
    }

    @Override
    public Optional<Produto> findById(Long id) {
        return jpaProdutoRepository.findById(id);
    }

    @Override
    public List<Produto> findAll() {
        return jpaProdutoRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        jpaProdutoRepository.deleteById(id);
    }
}
