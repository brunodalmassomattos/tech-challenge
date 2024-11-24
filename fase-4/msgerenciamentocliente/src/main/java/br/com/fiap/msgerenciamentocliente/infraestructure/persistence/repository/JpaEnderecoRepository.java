package br.com.fiap.msgerenciamentocliente.infraestructure.persistence.repository;

import br.com.fiap.msgerenciamentocliente.infraestructure.persistence.entity.EnderecoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaEnderecoRepository extends JpaRepository<EnderecoEntity, UUID> {
}