package br.com.fiap.pedido.application.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class PedidoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Nested
	class testeCriacaoPedido {

		@Test
		public void testeCriaPedido() throws Exception {

			mockMvc.perform(post("/api/pedido").contentType(MediaType.APPLICATION_JSON).content(
					"[{\"produto_id\": \"a73b2c4d-1a78-4b9f-9c9d-7d2b7f9c1234\",\"quantidade\": 2,\"usuario_id\": \"1fa5f9c2-7d12-4e11-8149-7f9e859aec33\",\"forma_pagamento\": \"PIX\"}]"))
					.andExpect(status().isOk());
		}

	}

	@Nested
	class testeBuscaPedido {

		@Test
		public void buscaPedidoByUsuarioId() throws Exception {

			String usuarioId = "1fa5f9c2-7d12-4e11-8149-7f9e859aec33";

			mockMvc.perform(get("/api/pedido/busca/usuario/" + usuarioId)).andExpect(status().isOk())
					.andExpect(jsonPath("$[0].usuario_id").value(usuarioId));

		}

		@Test
		public void testBuscaPedidoStatus() throws Exception {
			String status = "Aguardando Pagamento";

			mockMvc.perform(get("/api/pedido/busca/status/" + status)).andExpect(status().isOk())
					.andExpect(jsonPath("$[0].status").value(status));
		}

		@Test
		public void testBuscaNotaFiscal() throws Exception {
			String idNota = "2441899273";

			mockMvc.perform(get("/api/pedido/busca/notafiscal/" + idNota)).andExpect(status().isOk())
					.andExpect(jsonPath("$[0].nota_fiscal").value(idNota));
		}

	}
	
	@Nested
	class testeAlteraPedido {
		
		@Test
		public void testAlteraPedido() throws Exception {
			String idPedido = "1b9df22e-b90c-47ea-81cc-39872075c251";

			mockMvc.perform(post("/api/pedido/atualiza/id/" + idPedido).contentType(MediaType.APPLICATION_JSON)
					.content("{\"produto_id\":\"97bcc07c-9da2-4f89-9996-bde4cd1b5d97\",\"quantidade\":30,\"forma_pagamento\":\"Credito\"}")).andExpect(status().isOk());
		}

		@Test
		public void testAtualizaStatusPedido() throws Exception {
			String idNota = "2441899273";
			String status = "Pago";

			mockMvc.perform(post("/api/pedido/atualiza/status/" + idNota).param("status", status))
					.andExpect(status().isOk());
		}

	}
	
	@Nested
	class testeDeletaProduto{
		
	    @Test
	    public void testDeletaPedidoId() throws Exception {
	        String idPedido = "e0d3af96-e8b1-4b23-96fc-7b8a8018e7d0";

	        mockMvc.perform(delete("/api/pedido/deleta/id/" + idPedido))
	                .andExpect(status().isOk());
	    }

	    @Test
	    public void testDeletaPedidoNotaFiscal() throws Exception {
	        String notaFiscal = "6070687163";

	        mockMvc.perform(delete("/api/pedido/deleta/notafiscal/" + notaFiscal))
	                .andExpect(status().isOk());
	    }
		
	}

}
