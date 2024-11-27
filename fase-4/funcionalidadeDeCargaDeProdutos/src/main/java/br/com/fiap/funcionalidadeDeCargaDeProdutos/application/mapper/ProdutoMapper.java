package br.com.fiap.funcionalidadeDeCargaDeProdutos.application.mapper;

import br.com.fiap.funcionalidadeDeCargaDeProdutos.application.dto.ProdutoDTO;
import br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.entity.Produto;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapper {

    public Produto toEntity(ProdutoDTO dto) {
        if (dto == null) {
            return null;
        }

        Produto produto = new Produto();
        produto.setId(dto.getId());
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setQtdEstoque(dto.getQtdEstoque());

        return produto;
    }

    public ProdutoDTO toDto(Produto entity) {
        if (entity == null) {
            return null;
        }

        ProdutoDTO dto = new ProdutoDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setDescricao(entity.getDescricao());
        dto.setPreco(entity.getPreco());
        dto.setQtdEstoque(entity.getQtdEstoque());
        dto.setCategoriaId(entity.getCategoria().getId());

        return dto;
    }
}

