package br.com.fiap.newparquimetro.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.newparquimetro.dto.controletempo.ControleTempoResponseDTO;
import br.com.fiap.newparquimetro.service.ControleTempoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/tempo")
@AllArgsConstructor
public class TempoController {

	private ControleTempoService controTempoService;
	
	@GetMapping("/{idCondutor}")
	public ResponseEntity<List<ControleTempoResponseDTO>> buscaTempo(@Valid @PathVariable String idCondutor, @RequestHeader String status){
		return ResponseEntity.ok(this.controTempoService.buscaTempo(idCondutor, status));
	}
	
	@PostMapping
	public ResponseEntity<ControleTempoResponseDTO> cadastroTempo(@Valid @RequestHeader String idCondutor, @RequestHeader Long tempo){
		return ResponseEntity.ok(this.controTempoService.save(idCondutor,tempo));
		
	}
	
	@PatchMapping("/{idCondutor}")
	ResponseEntity<ControleTempoResponseDTO> atualizaTempo(@Valid @PathVariable String idCondutor, @RequestHeader Long tempo){
		return ResponseEntity.ok(this.controTempoService.update(idCondutor,tempo));
	}
	
}
