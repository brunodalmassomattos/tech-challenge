package br.com.fiap.funcionalidadeDeCargaDeProdutos.application.mapper;

import br.com.fiap.funcionalidadeDeCargaDeProdutos.application.dto.CategoriaDTO;
import br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.entity.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {

    public Categoria toEntity(CategoriaDTO dto) {
        if (dto == null) {
            return null;
        }

        Categoria categoria = new Categoria();
        categoria.setId(dto.id());
        categoria.setDescricao(dto.descricao());
        return categoria;
    }

    public CategoriaDTO toDto(Categoria entity) {
        if (entity == null) {
            return null;
        }

        return new CategoriaDTO(
                entity.getId(),
                entity.getDescricao()
        );
    }
}