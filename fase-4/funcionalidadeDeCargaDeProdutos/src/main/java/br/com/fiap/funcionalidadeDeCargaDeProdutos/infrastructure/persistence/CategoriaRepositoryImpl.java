package br.com.fiap.funcionalidadeDeCargaDeProdutos.infrastructure.persistence;

import br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.entity.Categoria;
import br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CategoriaRepositoryImpl implements CategoriaRepository {

    private final JpaCategoriaRepository jpaCategoriaRepository;

    @Override
    public Categoria save(Categoria categoria) {
        return jpaCategoriaRepository.save(categoria);
    }

    @Override
    public Optional<Categoria> findById(Long id) {
        return jpaCategoriaRepository.findById(id);
    }

    @Override
    public List<Categoria> findAll() {
        return jpaCategoriaRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        jpaCategoriaRepository.deleteById(id);
    }
}
