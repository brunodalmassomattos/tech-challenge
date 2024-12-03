package br.com.fiap.entrega.persistence.data;

import br.com.fiap.entrega.domain.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DataEnderecoRepository extends JpaRepository<Endereco, UUID> {
}
