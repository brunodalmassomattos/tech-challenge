package br.com.fiap.funcionalidadeDeCargaDeProdutos.infrastructure.persistence;

import br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaProdutoRepository extends JpaRepository<Produto, Long> {
}
