package br.com.fiap.pedido.application.dtos;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import br.com.fiap.pedido.domain.models.Pedido;

public record PedidoRequestDTO(String id, String produto_id, int quantidade, String valor, String usuario_id,
		String forma_pagamento, String nota_fiscal, String data_compra, String status, String codigo_entrega) {


	public static List<Pedido> prepararRequests(String idPedido, List<PedidoRequestDTO> pedidoRequestDTOs) throws ParseException {

	    return pedidoRequestDTOs.stream()
	            .map(dto -> Pedido.builder()
	                    .produto_id(UUID.fromString(dto.produto_id))
	                    .quantidade(dto.quantidade)
	                    .usuario_id(UUID.fromString(dto.usuario_id))
	                    .forma_pagamento(dto.forma_pagamento)
	                    .build()
	            )
	            .collect(Collectors.toList());
	}
	
	public static Pedido prepararRequest(String idPedido, PedidoRequestDTO pedidoRequestDTO) throws ParseException {
	    return Pedido.builder()
	            .produto_id(pedidoRequestDTO.produto_id != null ? UUID.fromString(pedidoRequestDTO.produto_id) : null)
	            .quantidade(pedidoRequestDTO.quantidade != 0 ? pedidoRequestDTO.quantidade : 0)
	            .forma_pagamento(pedidoRequestDTO.forma_pagamento != null ? pedidoRequestDTO.forma_pagamento : null)
	            .build();
	}


}
