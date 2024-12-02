package br.com.fiap.entrega.application.controller;

import br.com.fiap.entrega.application.dto.*;
import br.com.fiap.entrega.domain.service.EntregaService;
import br.com.fiap.entrega.domain.service.LoteService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/entrega")
@AllArgsConstructor
public class EntregaController {

    private final EntregaService entregaService;
    private final LoteService loteService;

    @PutMapping("/alterar-situacao/{id}")
    public ResponseEntity<EntregaResponseDto> alterarSituacaoEntrega(@PathVariable UUID id,
                                                                     @Schema(enumAsRef = true,
                                                                             type = "string",
                                                                             allowableValues = { "Problema na entrega", "Em rota de entrega", "Entregue ao destino" })
                                                                     @RequestParam String situacao) {
        return ResponseEntity.ok(entregaService.atualizarSituacao(id, situacao));
    }

    @PutMapping("/atribuir-entregador/{id}")
    public ResponseEntity<EntregaResponseDto> atribuirEntregador(@PathVariable UUID id,
                                                                     @RequestParam String entregador) {
        return ResponseEntity.ok(entregaService.atribuirEntregador(id, entregador));
    }

    @PutMapping("/atualizar-localizacao/{id}")
    public ResponseEntity<EntregaResponseDto> atualizarLocalizacaoEntrega(@PathVariable UUID id,
                                                                          @RequestBody LocalizacaoDto dto) {
        return ResponseEntity.ok(entregaService.atualizarLocalizacaoEntrega(id, dto));
    }

    @PutMapping("/alterar-situacao-lote/{loteId}")
    public ResponseEntity<LoteEntregaResponseDto> alterarSituacaoEntregasPorLote(@PathVariable UUID loteId,
                                                                                 @Schema(type = "string", allowableValues = {"Problema na entrega", "Em separação", "Despachado", "Entrada na filial"})
                                                                                 @RequestParam String situacao) {
        return ResponseEntity.ok(loteService.atualizarSituacao(loteId, situacao));
    }

    @PutMapping("/atribuir-transportadora/{loteId}")
    public ResponseEntity<LoteEntregaResponseDto> atribuirTrasportadora(@PathVariable UUID loteId,
                                                                        @RequestParam String transportadora) {
        return ResponseEntity.ok(loteService.atribuirTransportadora(loteId, transportadora));
    }

    @PutMapping("/atualizar-localizacao-lote/{loteId}")
    public ResponseEntity<LoteEntregaResponseDto> atualizarLocalizacaoLote(@PathVariable UUID loteId,
                                                                           @RequestBody LocalizacaoDto dto) {
        return ResponseEntity.ok(loteService.atualizarLocalizacao(loteId, dto));
    }

    @GetMapping("/entregas-lote/{loteId}")
    public ResponseEntity<LoteEntregaResponseDto> buscarEntregasPorLote(@PathVariable UUID loteId) {
        return ResponseEntity.ok(loteService.buscarEntregas(loteId));
    }

    @GetMapping("/entrega-codigoRastreio/{codigoRastreio}")
    public ResponseEntity<EntregaResponseDto> buscarEntregaPorCodigoRastreio(@PathVariable String codigoRastreio) {
        return ResponseEntity.ok(entregaService.buscarEntregaPorCodigoRastreio(codigoRastreio));
    }
}
