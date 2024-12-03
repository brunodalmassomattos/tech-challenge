package br.com.fiap.pedido.domain.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fiap.pedido.application.dtos.PedidoResponseDTO;
import br.com.fiap.pedido.domain.models.Pedido;
import br.com.fiap.pedido.infrastructure.external.client.CatalogoClient;
import br.com.fiap.pedido.infrastructure.persistence.PedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PedidoService {

	private PedidoRepository pedidoRepository;

	private CatalogoClient catalogoClient;

	private KafkaProducer kafkaProducer;

	public List<PedidoResponseDTO> salvar(List<Pedido> pedidos) throws Exception {

		String notaFiscal = gerarNumeroNotaFiscal(10);

		while (pedidoRepository.buscaPedidoNotaFiscal(notaFiscal).size() > 0) {
			notaFiscal = gerarNumeroNotaFiscal(10);
		}

		List<Pedido> pedidosCriados = new ArrayList<>();

		for (Pedido pedido : pedidos) {

			Map<String, Object> map = formataRetornoProduto(pedido);

			validQuantidadeEstoque(pedido.getQuantidade(), Integer.parseInt(map.get("qtdEstoque").toString()));

			float valorTotal = Float.parseFloat(map.get("preco").toString()) * pedido.getQuantidade();
			pedido.setValor(String.valueOf(valorTotal));
			pedido.setNota_fiscal(notaFiscal);
			pedido.setData_compra(new Date());
			pedido.setStatus("Aguardando Pagamento");

			pedidosCriados.add(pedido);

			catalogoClient.baixaEstoque(pedido.getProduto_id().toString(), pedido.getQuantidade());

		}

		try {
			return PedidoResponseDTO.toDtoList(pedidoRepository.saveAll(pedidosCriados));
		} catch (Exception e) {
			throw new RuntimeException("Erro ao salvar os pedidos", e);
		}
	}

	public List<PedidoResponseDTO> buscaPedidoUsuario(String idUsuario) {
		return PedidoResponseDTO.toDtoList(pedidoRepository.buscaPedidoidUsuarioId(UUID.fromString(idUsuario)));
	}

	public List<PedidoResponseDTO> buscaPedidoNotaFiscal(String idNota) {
		return PedidoResponseDTO.toDtoList(pedidoRepository.buscaPedidoNotaFiscal(idNota));
	}

	public List<PedidoResponseDTO> buscaPedidoStatus(String status) {
		return PedidoResponseDTO.toDtoList(pedidoRepository.buscaPedidoStatus(status));
	}

	public PedidoResponseDTO buscaPedidoId(String idPedido) {

		Pedido pedido = pedidoRepository.findById(UUID.fromString(idPedido))
				.orElseThrow(() -> new EntityNotFoundException("Id do pedido não encontrado: " + idPedido));

		return PedidoResponseDTO.toDto(pedido);
	}

	public PedidoResponseDTO alterarPedido(Pedido pedidoRequest, String idPedido) {

		Pedido pedido = pedidoRepository.findById(UUID.fromString(idPedido))
				.orElseThrow(() -> new EntityNotFoundException("Id do eedido não encontrado: " + idPedido));

		verificarStatusPedidoCancelamento(pedido.getStatus());

		if (!(pedidoRequest.getQuantidade() == 0) && !(pedidoRequest.getProduto_id() == null)) {

			Map<String, Object> map = formataRetornoProduto(pedidoRequest);

			validQuantidadeEstoque(pedidoRequest.getQuantidade(), Integer.parseInt(map.get("qtdEstoque").toString()));

			catalogoClient.estornoEstoque(pedido.getProduto_id().toString(), pedido.getQuantidade());
			
			float valorTotal = Float.parseFloat(map.get("preco").toString()) * pedidoRequest.getQuantidade();
			
			pedido.setValor(String.valueOf(valorTotal));
			pedido.setQuantidade(pedidoRequest.getQuantidade());
			pedido.setProduto_id(pedidoRequest.getProduto_id() != pedido.getProduto_id() ? pedidoRequest.getProduto_id()
					: pedido.getProduto_id());
			
			catalogoClient.baixaEstoque(pedidoRequest.getProduto_id().toString(), pedidoRequest.getQuantidade());

		}

		pedido.setForma_pagamento(pedidoRequest.getForma_pagamento() != null ? pedidoRequest.getForma_pagamento()
				: pedido.getForma_pagamento());

		return PedidoResponseDTO.toDto(pedidoRepository.save(pedido));
	}

	public List<PedidoResponseDTO> atualizaStatusPedido(String idNota, String status) {

		pedidoRepository.updateStatusPedido(idNota, status);

		List<Pedido> pedidos = pedidoRepository.buscaPedidoNotaFiscal(idNota);

		if (status.equals("Pago")) {

			String event = "{\"event\":\"Pagamento Confirmado\",\"notaFiscal\":\"" + pedidos.get(0).getNota_fiscal()
					+ "\",\"idCliente\":\"" + pedidos.get(0).getUsuario_id().toString() + "\"}";
			kafkaProducer.sendMessage("pedidos", event);
		}

		return PedidoResponseDTO.toDtoList(pedidos);

	}

	public PedidoResponseDTO deletarPedidoId(String idPedido) throws Exception {

		Pedido pedido = pedidoRepository.findById(UUID.fromString(idPedido))
				.orElseThrow(() -> new EntityNotFoundException("Id do pedido não encontrado: " + idPedido));

		verificarStatusPedidoCancelamento(pedido.getStatus());

		catalogoClient.estornoEstoque(pedido.getProduto_id().toString(), pedido.getQuantidade());

		pedidoRepository.delete(pedido);

		return PedidoResponseDTO.toDto(pedido);
	}

	public List<PedidoResponseDTO> deletarPedidoNotaFiscal(String notaFiscal) throws Exception {

		List<Pedido> pedidos = pedidoRepository.buscaPedidoNotaFiscal(notaFiscal);

		if (pedidos.isEmpty()) {
			throw new EntityNotFoundException("Nenhum pedido encontrado com a Nota Fiscal: " + notaFiscal);
		}

		for (Pedido pedido : pedidos) {

			verificarStatusPedidoCancelamento(pedido.getStatus());

			catalogoClient.estornoEstoque(pedido.getProduto_id().toString(), pedido.getQuantidade());

			pedidoRepository.delete(pedido);

		}

		return PedidoResponseDTO.toDtoList(pedidos);
	}
	
	public void atualizaCodigoEntrega(String event) {
		
		System.out.println("Evento recebido: " + event);
		Map<String, Object> map = formataRetornoEventRastreio(event);
		List<Pedido> pedidos = pedidoRepository.buscaPedidoNotaFiscal(map.get("notaFiscal").toString());
		for(Pedido pedido : pedidos){
			pedido.setCodigo_entrega(UUID.fromString(map.get("codigoRastreio").toString()));
			pedidoRepository.save(pedido);
		}
		
	}

	public static String gerarNumeroNotaFiscal(int tamanho) {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < tamanho; i++) {
			sb.append(random.nextInt(10));
		}

		return sb.toString();
	}

	public void verificarStatusPedidoCancelamento(String status) {
		if (!status.equals("Aguardando Pagamento")) {
			throw new IllegalArgumentException("Nao foi possivel cancelar pois o pedido já esta em andamento");
		}
	}

	public Map<String, Object> formataRetornoProduto(Pedido pedido) {
	    try {
	        String detalheProduto = catalogoClient.buscarProdutoPorId(pedido.getProduto_id().toString());
	        
	        ObjectMapper objectMapper = new ObjectMapper();
	        return objectMapper.readValue(detalheProduto, Map.class);
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	public void validQuantidadeEstoque(Integer quantidadePedido, Integer quantidadeEstoque) {
		if (quantidadePedido > quantidadeEstoque) {
			throw new IllegalArgumentException("A quantidade solicitada ultrapassa a quantidade em estoque.");
		}
	}
	
	public Map<String, Object> formataRetornoEventRastreio(String event) {
		
		JSONObject jsonObject = new JSONObject(event);
		return jsonObject.toMap();
		
	}	
	

}
