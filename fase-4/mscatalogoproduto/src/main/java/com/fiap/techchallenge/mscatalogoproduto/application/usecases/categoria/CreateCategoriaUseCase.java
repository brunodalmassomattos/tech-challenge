package com.fiap.techchallenge.mscatalogoproduto.application.usecases.categoria;

import com.fiap.techchallenge.mscatalogoproduto.application.usecases.execption.CategoriaException;
import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Categoria;
import com.fiap.techchallenge.mscatalogoproduto.domain.repositories.CategoriaRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateCategoriaUseCase {
    private final CategoriaRepository categoriaRepository;

    public CreateCategoriaUseCase(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public Categoria execute(String descricao) {
        if (categoriaRepository.existsByDescricao(descricao)) {
            throw new CategoriaException("Já existe uma categoria com esta descrição.");
        }

        Categoria categoria = new Categoria();
        categoria.setDescricao(descricao.toLowerCase());

        return categoriaRepository.save(categoria);
    }
}
