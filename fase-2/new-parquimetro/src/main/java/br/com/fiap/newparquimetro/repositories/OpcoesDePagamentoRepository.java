package br.com.fiap.newparquimetro.repositories;

import br.com.fiap.newparquimetro.domain.opcoesDePagamento.OpcoesDePagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OpcoesDePagamentoRepository extends JpaRepository<OpcoesDePagamento, String> {
    List<OpcoesDePagamento> findByCondutorIdAndStatus(String condutorId, String status);

    Optional<OpcoesDePagamento> findById(String id);
}

