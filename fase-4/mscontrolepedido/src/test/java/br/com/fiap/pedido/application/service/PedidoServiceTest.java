package br.com.fiap.pedido.application.service;

import br.com.fiap.pedido.application.dtos.PedidoResponseDTO;
import br.com.fiap.pedido.domain.models.Pedido;
import br.com.fiap.pedido.domain.services.KafkaProducer;
import br.com.fiap.pedido.domain.services.PedidoService;
import br.com.fiap.pedido.infrastructure.external.client.CatalogoClient;
import br.com.fiap.pedido.infrastructure.persistence.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityNotFoundException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {

	@Mock
	private PedidoRepository pedidoRepository;

	@Mock
	private CatalogoClient catalogoClient;

	@Mock
	private KafkaProducer kafkaProducer;

	@InjectMocks
	private PedidoService pedidoService;

	private Pedido pedido;
	private UUID pedidoId;

	@BeforeEach
	void setUp() {
		pedidoId = UUID.randomUUID();
		pedido = new Pedido();
		pedido.setId(pedidoId);
		pedido.setUsuario_id(UUID.randomUUID());
		pedido.setProduto_id(UUID.randomUUID());
		pedido.setQuantidade(2);
		pedido.setValor("100.00");
		pedido.setStatus("Aguardando Pagamento");
	}

	@Test
	void salvar_DeveRetornarPedidosCriados() throws Exception {
		when(pedidoRepository.buscaPedidoNotaFiscal(anyString())).thenReturn(Collections.emptyList());
		when(catalogoClient.buscarProdutoPorId(anyString())).thenReturn("{\"preco\": 50.00, \"qtdEstoque\": 10}");
		when(pedidoRepository.saveAll(anyList())).thenReturn(Collections.singletonList(pedido));

		List<Pedido> pedidos = Collections.singletonList(pedido);
		List<PedidoResponseDTO> pedidosCriados = pedidoService.salvar(pedidos);

		assertNotNull(pedidosCriados);
		assertEquals(1, pedidosCriados.size());
		verify(pedidoRepository).saveAll(anyList());
	}

	@Test
	void buscaPedidoUsuario_DeveRetornarPedidosDoUsuario() {
		when(pedidoRepository.buscaPedidoidUsuarioId(any(UUID.class))).thenReturn(Collections.singletonList(pedido));

		List<PedidoResponseDTO> pedidos = pedidoService.buscaPedidoUsuario(pedido.getUsuario_id().toString());

		assertNotNull(pedidos);
		assertEquals(1, pedidos.size());
		verify(pedidoRepository).buscaPedidoidUsuarioId(any(UUID.class));
	}

	@Test
	void buscaPedidoId_DeveRetornarPedido() {
		when(pedidoRepository.findById(any(UUID.class))).thenReturn(Optional.of(pedido));

		PedidoResponseDTO pedidoEncontrado = pedidoService.buscaPedidoId(pedidoId.toString());

		assertNotNull(pedidoEncontrado);
		assertEquals(pedido.getId(), UUID.fromString(pedidoEncontrado.id()));
		verify(pedidoRepository).findById(any(UUID.class));
	}

	@Test
	void buscaPedidoId_DeveLancarExcecao_QuandoPedidoNaoEncontrado() {
		when(pedidoRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

		assertThrows(EntityNotFoundException.class, () -> {
			pedidoService.buscaPedidoId(pedidoId.toString());
		});
	}

	@Test
	void alterarPedido_DeveRetornarPedidoAtualizado() {
		when(pedidoRepository.findById(any(UUID.class))).thenReturn(Optional.of(pedido));
		when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);
		when(catalogoClient.buscarProdutoPorId(anyString())).thenReturn("{\"preco\": 50.00, \"qtdEstoque\": 10}");

		Pedido pedidoRequest = new Pedido();
		pedidoRequest.setQuantidade(3);
		pedidoRequest.setProduto_id(UUID.randomUUID());

		PedidoResponseDTO pedidoAtualizado = pedidoService.alterarPedido(pedidoRequest, pedidoId.toString());

		assertNotNull(pedidoAtualizado);
		assertEquals(3, pedido.getQuantidade());
		verify(pedidoRepository).save(any(Pedido.class));
	}

	@Test
	void deletarPedidoId_DeveDeletarPedidoComSucesso() throws Exception {
		when(pedidoRepository.findById(any(UUID.class))).thenReturn(Optional.of(pedido));

		PedidoResponseDTO pedidoDeletado = pedidoService.deletarPedidoId(pedidoId.toString());

		assertNotNull(pedidoDeletado);
		verify(pedidoRepository).delete(any(Pedido.class));
	}

}
