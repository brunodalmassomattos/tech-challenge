package br.com.fiap.funcionalidadeDeCargaDeProdutos.application.service;

import br.com.fiap.funcionalidadeDeCargaDeProdutos.application.dto.CategoriaDTO;
import java.util.List;

public interface CategoriaService {

    CategoriaDTO createCategoria(CategoriaDTO categoriaDTO);

    CategoriaDTO updateCategoria(Long id, CategoriaDTO categoriaDTO);

    void deleteCategoria(Long id);

    CategoriaDTO getCategoriaById(Long id);

    List<CategoriaDTO> getAllCategorias();
}

