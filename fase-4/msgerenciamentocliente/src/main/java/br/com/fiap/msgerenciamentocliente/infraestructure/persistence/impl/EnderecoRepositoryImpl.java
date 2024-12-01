package br.com.fiap.msgerenciamentocliente.infraestructure.persistence.impl;

import br.com.fiap.msgerenciamentocliente.domain.core.entity.Endereco;
import br.com.fiap.msgerenciamentocliente.domain.core.repository.EnderecoRepository;
import br.com.fiap.msgerenciamentocliente.infraestructure.persistence.mapper.EnderecoMapper;
import br.com.fiap.msgerenciamentocliente.infraestructure.persistence.repository.JpaEnderecoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class EnderecoRepositoryImpl implements EnderecoRepository {
    private final JpaEnderecoRepository jpaEnderecoRepository;
    private final EnderecoMapper enderecoMapper;

    public EnderecoRepositoryImpl(JpaEnderecoRepository jpaEnderecoRepository, EnderecoMapper enderecoMapper) {
        this.jpaEnderecoRepository = jpaEnderecoRepository;
        this.enderecoMapper = enderecoMapper;
    }

    @Override
    public Endereco save(Endereco endereco) {
        return enderecoMapper.toDomain(
                jpaEnderecoRepository.save(
                        enderecoMapper.toEntity(endereco)
                )
        );
    }

    @Override
    public List<Endereco> findAll() {
        return jpaEnderecoRepository.findAll()
                .stream()
                .map(enderecoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Endereco> findById(UUID id) {
        return jpaEnderecoRepository.findById(id)
                .map(enderecoMapper::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        jpaEnderecoRepository.deleteById(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return jpaEnderecoRepository.existsById(id);
    }
}