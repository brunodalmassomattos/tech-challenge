package br.com.fiap.pedido.application.dtos;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import br.com.fiap.pedido.domain.models.Pedido;

public record PedidoResponseDTO(String id, String produto_id, int quantidade, String valor, String usuario_id,
		String forma_pagamento, String nota_fiscal, String data_compra, String status, String codigo_entrega) {

	public static String parseDate(Date data) {
		if (data == null) {
			return null;
		}

		return new SimpleDateFormat("yyyy-MM-dd").format(data);
	}

	public static PedidoResponseDTO toDto(Pedido pedido) {
	    return new PedidoResponseDTO(
	        pedido.getId().toString(),
	        pedido.getProduto_id() != null ? pedido.getProduto_id().toString() : null,
	        pedido.getQuantidade(),
	        pedido.getValor(),
	        pedido.getUsuario_id() != null ? pedido.getUsuario_id().toString() : null,
	        pedido.getForma_pagamento(),
	        pedido.getNota_fiscal(),
	        PedidoResponseDTO.parseDate(pedido.getData_compra()),
	        pedido.getStatus(),
	        pedido.getCodigo_entrega() != null ? pedido.getCodigo_entrega().toString() : null
	    );
	}


	public static List<PedidoResponseDTO> toDtoList(List<Pedido> pedidos) {
		return pedidos.stream().map(PedidoResponseDTO::toDto).collect(Collectors.toList());
	}

}
