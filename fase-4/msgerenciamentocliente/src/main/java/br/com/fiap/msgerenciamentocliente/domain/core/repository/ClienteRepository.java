package br.com.fiap.msgerenciamentocliente.domain.core.repository;

import br.com.fiap.msgerenciamentocliente.domain.core.entity.Cliente;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClienteRepository {
    Cliente save(Cliente cliente);
    List<Cliente> findAll();
    Optional<Cliente> findById(UUID id);
    Optional<Cliente> findByCpf(String cpf);
    void deleteById(UUID id);
    boolean existsById(UUID id);
}