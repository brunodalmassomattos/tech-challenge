package br.com.fiap.msgerenciamentocliente.repository;

import br.com.fiap.msgerenciamentocliente.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EnderecoRepository extends JpaRepository<Endereco, UUID> {
}
