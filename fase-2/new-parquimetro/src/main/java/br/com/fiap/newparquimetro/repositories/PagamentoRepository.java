package br.com.fiap.newparquimetro.repositories;

import br.com.fiap.newparquimetro.domain.opcoesDePagamento.OpcoesDePagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagamentoRepository extends JpaRepository<OpcoesDePagamento, String> {
    List<OpcoesDePagamento> findByCondutorIdAndStatus(String condutorId, String status);
}