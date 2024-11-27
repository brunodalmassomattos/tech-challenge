package br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.repository;

import br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.entity.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository {

    Categoria save(Categoria categoria);

    Optional<Categoria> findById(Long id);

    List<Categoria> findAll();

    void deleteById(Long id);
}
