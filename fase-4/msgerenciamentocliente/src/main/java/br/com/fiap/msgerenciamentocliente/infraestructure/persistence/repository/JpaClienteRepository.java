package br.com.fiap.msgerenciamentocliente.infraestructure.persistence.repository;

import br.com.fiap.msgerenciamentocliente.infraestructure.persistence.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface JpaClienteRepository extends JpaRepository<ClienteEntity, UUID> {
    @Query("SELECT c FROM ClienteEntity c WHERE c.cpf = :cpf")
    Optional<ClienteEntity> findByCpf(@Param("cpf") String cpf);
}