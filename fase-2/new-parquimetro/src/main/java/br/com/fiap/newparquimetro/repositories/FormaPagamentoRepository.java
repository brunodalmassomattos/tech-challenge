package br.com.fiap.newparquimetro.repositories;

import br.com.fiap.newparquimetro.domain.formapagamento.FormaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, String> {
}
