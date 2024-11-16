package br.com.fiap.msgerenciamentocliente.repository;

import br.com.fiap.msgerenciamentocliente.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    Cliente findByCpf(String cpf);
}
