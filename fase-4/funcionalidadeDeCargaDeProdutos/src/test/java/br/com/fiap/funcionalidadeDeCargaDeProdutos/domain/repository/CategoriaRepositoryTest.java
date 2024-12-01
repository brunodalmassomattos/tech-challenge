package br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.repository;

import br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.entity.Categoria;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CategoriaRepositoryTest {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Test
    public void whenSaveAndFindCategoriaTest() {
        Categoria categoria = new Categoria();
        categoria.setDescricao("Categoria Nova");

        categoria = categoriaRepository.save(categoria);

        Optional<Categoria> found = categoriaRepository.findById(categoria.getId());

        assertTrue(found.isPresent());
        assertEquals("Categoria Nova", found.get().getDescricao());
    }

    @Test
    public void whenFindByDescricaoTest() {
        Categoria categoria = new Categoria();
        categoria.setDescricao("Categoria Única");

        categoriaRepository.save(categoria);

        Optional<Categoria> found = categoriaRepository.findByDescricao("Categoria Única");

        assertTrue(found.isPresent());
        assertEquals(categoria.getId(), found.get().getId());
    }
}

