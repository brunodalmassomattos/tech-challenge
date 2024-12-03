package br.com.fiap.pedido.application.controllers;

import java.text.ParseException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.pedido.application.dtos.PedidoRequestDTO;
import br.com.fiap.pedido.application.dtos.PedidoResponseDTO;
import br.com.fiap.pedido.domain.services.PedidoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/pedido")
@AllArgsConstructor
public class PedidoController {

	private final PedidoService pedidoService;

	@PostMapping
	public ResponseEntity<List<PedidoResponseDTO>> criaPedido(@Valid @RequestBody List<PedidoRequestDTO> pedidoRequestDTO)
			throws Exception {
		return ResponseEntity.ok(this.pedidoService.salvar(PedidoRequestDTO.prepararRequests(null, pedidoRequestDTO)));

	}

	@GetMapping("/busca/usuario/{idUsuario}")
	public ResponseEntity<List<PedidoResponseDTO>> buscaPedidoCliente(@PathVariable String idUsuario) {
		return ResponseEntity.ok(this.pedidoService.buscaPedidoUsuario(idUsuario));

	}

	@GetMapping("/busca/status/{status}")
	public ResponseEntity<List<PedidoResponseDTO>> buscaPedidoStatus(@PathVariable String status) {
		return ResponseEntity.ok(this.pedidoService.buscaPedidoStatus(status));

	}
	
	@GetMapping("/busca/notafiscal/{idNota}")
	public ResponseEntity<List<PedidoResponseDTO>> buscaNotaFiscal(@PathVariable String idNota) {
		return ResponseEntity.ok(this.pedidoService.buscaPedidoNotaFiscal(idNota));

	}
	
	@PostMapping("/atualiza/id/{idPedido}")
	public ResponseEntity<PedidoResponseDTO> alteraPedido(@PathVariable String idPedido,
			@Valid @RequestBody PedidoRequestDTO pedidoRequestDTO) throws ParseException {
		return ResponseEntity
				.ok(this.pedidoService.alterarPedido(PedidoRequestDTO.prepararRequest(null, pedidoRequestDTO),idPedido));

	}
	
	@PostMapping("/atualiza/status/{idNota}")
	public ResponseEntity<List<PedidoResponseDTO>> atualizaStatusPedido(@PathVariable String idNota, @RequestParam String status){
		return ResponseEntity.ok(this.pedidoService.atualizaStatusPedido(idNota,status));
		
	}
	
	@DeleteMapping("/deleta/id/{idPedido}")
	public ResponseEntity<PedidoResponseDTO> deletaPedidoId(@PathVariable String idPedido) throws Exception{
		return ResponseEntity.ok(this.pedidoService.deletarPedidoId(idPedido));
	}
	
	@DeleteMapping("/deleta/notafiscal/{notaFiscal}")
	public ResponseEntity<List<PedidoResponseDTO>> deletaPedidoNotaFiscal(@PathVariable String notaFiscal) throws Exception{
		return ResponseEntity.ok(this.pedidoService.deletarPedidoNotaFiscal(notaFiscal));
	}

}
