package br.com.fiap.funcionalidadeDeCargaDeProdutos.application.mapper;

import br.com.fiap.funcionalidadeDeCargaDeProdutos.application.dto.ProdutoDTO;
import br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.entity.Produto;
import br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.entity.Categoria;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapper {

    public Produto toEntity(ProdutoDTO dto, Categoria categoria) {
        if (dto == null) {
            return null;
        }

        Produto produto = new Produto();
        produto.setId(dto.id());
        produto.setNome(dto.nome());
        produto.setDescricao(dto.descricao());
        produto.setPreco(dto.preco());
        produto.setQtdEstoque(dto.qtdEstoque());
        produto.setCategoria(categoria);
        return produto;
    }

    public ProdutoDTO toDto(Produto entity) {
        if (entity == null) {
            return null;
        }

        return new ProdutoDTO(
                entity.getId(),
                entity.getNome(),
                entity.getDescricao(),
                entity.getPreco(),
                entity.getQtdEstoque(),
                entity.getCategoria().getId()
        );
    }
}