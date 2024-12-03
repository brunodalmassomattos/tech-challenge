package br.com.fiap.mslogistica.persistence.data;

import br.com.fiap.mslogistica.domain.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DataEnderecoRepository extends JpaRepository<Endereco, UUID> {
}
