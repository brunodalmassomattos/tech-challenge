package br.com.fiap.funcionalidadeDeCargaDeProdutos.application.service;

import br.com.fiap.funcionalidadeDeCargaDeProdutos.application.dto.CategoriaDTO;

import java.util.List;
import java.util.UUID;

public interface CategoriaService {

    CategoriaDTO createCategoria(CategoriaDTO categoriaDTO);

    CategoriaDTO updateCategoria(UUID id, CategoriaDTO categoriaDTO);

    void deleteCategoria(UUID id);

    CategoriaDTO getCategoriaById(UUID id);

    List<CategoriaDTO> getAllCategorias();
}