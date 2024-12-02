package br.com.fiap.msgerenciamentocliente.domain.core.repository;

import br.com.fiap.msgerenciamentocliente.domain.core.entity.Endereco;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EnderecoRepository {
    Endereco save(Endereco endereco);
    List<Endereco> findAll();
    Optional<Endereco> findById(UUID id);
    void deleteById(UUID id);
    boolean existsById(UUID id);
}