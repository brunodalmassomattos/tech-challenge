package br.com.fiap.funcionalidadeDeCargaDeProdutos.application.service;

import br.com.fiap.funcionalidadeDeCargaDeProdutos.application.dto.ProdutoDTO;
import br.com.fiap.funcionalidadeDeCargaDeProdutos.application.mapper.ProdutoMapper;
import br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.entity.Categoria;
import br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.entity.Produto;
import br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.exception.ResourceNotFoundException;
import br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.repository.CategoriaRepository;
import br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProdutoMapper produtoMapper;

    @Override
    @Transactional
    public ProdutoDTO createProduto(ProdutoDTO produtoDTO) {
        Categoria categoria = validarCategoria(produtoDTO.categoriaId());
        Produto produto = produtoMapper.toEntity(produtoDTO, categoria);
        Produto salvo = produtoRepository.save(produto);
        return produtoMapper.toDto(salvo);
    }

    @Override
    @Transactional
    public ProdutoDTO updateProduto(UUID id, ProdutoDTO produtoDTO) {
        Produto existente = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com id " + id));

        Categoria categoria = validarCategoria(produtoDTO.categoriaId());
        existente.setNome(produtoDTO.nome());
        existente.setDescricao(produtoDTO.descricao());
        existente.setPreco(produtoDTO.preco());
        existente.setQtdEstoque(produtoDTO.qtdEstoque());
        existente.setCategoria(categoria);

        Produto atualizado = produtoRepository.save(existente);
        return produtoMapper.toDto(atualizado);
    }

    @Override
    @Transactional
    public void deleteProduto(UUID id) {
        if (!produtoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produto não encontrado com id " + id);
        }
        produtoRepository.deleteById(id);
    }

    @Override
    public ProdutoDTO getProdutoById(UUID id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com id " + id));
        return produtoMapper.toDto(produto);
    }

    @Override
    public List<ProdutoDTO> getAllProdutos() {
        List<Produto> produtos = produtoRepository.findAll();
        return produtos.stream()
                .map(produtoMapper::toDto)
                .collect(Collectors.toList());
    }

    private Categoria validarCategoria(UUID categoriaId) {
        return categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com id " + categoriaId));
    }
}