// infrastructure/persistence/impl/ClienteRepositoryImpl.java
package br.com.fiap.msgerenciamentocliente.infraestructure.persistence.impl;

import br.com.fiap.msgerenciamentocliente.domain.core.entity.Cliente;
import br.com.fiap.msgerenciamentocliente.domain.core.repository.ClienteRepository;
import br.com.fiap.msgerenciamentocliente.infraestructure.persistence.mapper.ClienteMapper;
import br.com.fiap.msgerenciamentocliente.infraestructure.persistence.repository.JpaClienteRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class ClienteRepositoryImpl implements ClienteRepository {
    private final JpaClienteRepository jpaClienteRepository;
    private final ClienteMapper clienteMapper;

    public ClienteRepositoryImpl(JpaClienteRepository jpaClienteRepository, ClienteMapper clienteMapper) {
        this.jpaClienteRepository = jpaClienteRepository;
        this.clienteMapper = clienteMapper;
    }

    @Override
    public Cliente save(Cliente cliente) {
        return clienteMapper.toDomain(
                jpaClienteRepository.save(
                        clienteMapper.toEntity(cliente)
                )
        );
    }

    @Override
    public List<Cliente> findAll() {
        return jpaClienteRepository.findAll()
                .stream()
                .map(clienteMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Cliente> findById(UUID id) {
        return jpaClienteRepository.findById(id)
                .map(clienteMapper::toDomain);
    }

    @Override
    public Optional<Cliente> findByCpf(String cpf) {
        return jpaClienteRepository.findByCpf(cpf)
                .map(clienteMapper::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        jpaClienteRepository.deleteById(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return jpaClienteRepository.existsById(id);
    }
}