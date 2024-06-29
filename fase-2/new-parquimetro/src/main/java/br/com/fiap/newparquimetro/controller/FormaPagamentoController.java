package br.com.fiap.newparquimetro.controller;

import br.com.fiap.newparquimetro.domain.formapagamento.FormaPagamento;
import br.com.fiap.newparquimetro.dto.FormaPagamentoResponseDTO;
import br.com.fiap.newparquimetro.service.FormaPagamentoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/forma-pagamento")
@AllArgsConstructor
public class FormaPagamentoController {

    private final FormaPagamentoService formaPagamentoService;

    @PostMapping
    public ResponseEntity<FormaPagamentoResponseDTO> formaPagamento(@Valid @RequestHeader String tipoPagamento) throws ParseException {
        return ResponseEntity.ok(this.formaPagamentoService.save(FormaPagamento.builder().tipo(tipoPagamento).build()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormaPagamentoResponseDTO> BuscarFormaPagamento(@Valid @PathVariable String id) throws ParseException {
        return ResponseEntity.ok(FormaPagamentoResponseDTO.toDTO(this.formaPagamentoService.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FormaPagamentoResponseDTO> formaPagamento(@Valid @PathVariable String id, @RequestHeader String tipoPagamento) throws ParseException {
        return ResponseEntity.ok(this.formaPagamentoService.update(FormaPagamento.builder().id(id).tipo(tipoPagamento).build()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        this.formaPagamentoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
