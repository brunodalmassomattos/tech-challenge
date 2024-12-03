package br.com.fiap.pedido.infrastructure.persistence;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.fiap.pedido.domain.models.Pedido;
import jakarta.transaction.Transactional;

public interface PedidoRepository extends JpaRepository<Pedido,UUID>{

	@Query(value = "SELECT * FROM pedido WHERE usuario_id = :idUsuario", nativeQuery = true)
	List<Pedido> buscaPedidoidUsuarioId(@Param("idUsuario") UUID idUsuario);
	
	@Query(value = "SELECT * FROM pedido WHERE status = :status", nativeQuery = true)
	List<Pedido> buscaPedidoStatus(@Param("status") String status);
	
	@Query(value = "SELECT * FROM pedido WHERE nota_fiscal = :notaFiscal", nativeQuery = true)
	List<Pedido> buscaPedidoNotaFiscal(@Param("notaFiscal") String notaFiscal);
	
	@Modifying
    @Transactional
	@Query(value = "UPDATE pedido SET status = :status where nota_fiscal = :notaFiscal", nativeQuery = true)
	void updateStatusPedido(@Param("notaFiscal") String notaFiscal, @Param("status") String status);
	
}
