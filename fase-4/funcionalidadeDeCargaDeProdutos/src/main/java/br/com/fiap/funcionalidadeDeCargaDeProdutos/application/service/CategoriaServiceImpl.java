package br.com.fiap.funcionalidadeDeCargaDeProdutos.application.service;

import br.com.fiap.funcionalidadeDeCargaDeProdutos.application.dto.CategoriaDTO;
import br.com.fiap.funcionalidadeDeCargaDeProdutos.application.mapper.CategoriaMapper;
import br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.entity.Categoria;
import br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.exception.ResourceNotFoundException;
import br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    @Override
    @Transactional
    public CategoriaDTO createCategoria(CategoriaDTO categoriaDTO) {
        Categoria categoria = categoriaMapper.toEntity(categoriaDTO);
        Categoria salva = categoriaRepository.save(categoria);
        return categoriaMapper.toDto(salva);
    }

    @Override
    @Transactional
    public CategoriaDTO updateCategoria(UUID id, CategoriaDTO categoriaDTO) {
        Categoria existente = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com id " + id));

        existente.setDescricao(categoriaDTO.descricao());

        Categoria atualizada = categoriaRepository.save(existente);
        return categoriaMapper.toDto(atualizada);
    }

    @Override
    @Transactional
    public void deleteCategoria(UUID id) {
        if (!categoriaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoria não encontrada com id " + id);
        }
        categoriaRepository.deleteById(id);
    }

    @Override
    public CategoriaDTO getCategoriaById(UUID id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com id " + id));
        return categoriaMapper.toDto(categoria);
    }

    @Override
    public List<CategoriaDTO> getAllCategorias() {
        List<Categoria> categorias = categoriaRepository.findAll();
        return categorias.stream()
                .map(categoriaMapper::toDto)
                .collect(Collectors.toList());
    }
}