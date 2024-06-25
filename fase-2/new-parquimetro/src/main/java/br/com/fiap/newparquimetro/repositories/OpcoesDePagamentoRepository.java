package br.com.fiap.newparquimetro.repositories;

import br.com.fiap.newparquimetro.domain.opcoesDePagamento.OpcoesDePagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpcoesDePagamentoRepository extends JpaRepository<OpcoesDePagamento, String> {
}
