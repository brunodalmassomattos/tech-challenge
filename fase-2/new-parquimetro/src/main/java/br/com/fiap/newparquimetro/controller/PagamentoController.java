package br.com.fiap.newparquimetro.controller;

import br.com.fiap.newparquimetro.domain.condutor.Condutor;
import br.com.fiap.newparquimetro.domain.opcoesDePagamento.OpcoesDePagamento;
import br.com.fiap.newparquimetro.dto.opcaopagamentos.CriarOpcaoPagamentoDTO;
import br.com.fiap.newparquimetro.dto.opcaopagamentos.OpcoesDePagamentoDTO;
import br.com.fiap.newparquimetro.dto.opcaopagamentos.OpcoesDePagamentoListDTO;
import br.com.fiap.newparquimetro.service.PagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagamento")
public class PagamentoController {

    @Autowired
    private PagamentoService service;

    @GetMapping("/{id}")
    public ResponseEntity<OpcoesDePagamentoDTO> buscarPorIdCondutor(@PathVariable String id) {
        OpcoesDePagamento opcao = service.findById(id);
        return ResponseEntity.ok(OpcoesDePagamentoDTO.toDTO(opcao));
    }

    @GetMapping("/pendentes/by-condutor/{condutorId}")
    public ResponseEntity<List<OpcoesDePagamentoListDTO>> listarPendentesPorCondutorId(@PathVariable String condutorId) {
        List<OpcoesDePagamentoListDTO> pagamentos = service.findPendentesByCondutorId(condutorId);
        return ResponseEntity.ok(pagamentos);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<OpcoesDePagamentoDTO> criarPagamento(@RequestBody @Valid CriarOpcaoPagamentoDTO dados) {
        OpcoesDePagamentoDTO salva = service.save(OpcoesDePagamento.builder()
                .idTempo(dados.getIdTempo())
                .dataPagamento(dados.getDataPagamento())
                .status(dados.getStatus())
                .valor(dados.getValor())
                .condutor(Condutor.builder().id(dados.getIdCondutor()).build())
                .build());
        return ResponseEntity.ok(salva);
    }

    @PostMapping("/simular-pagamento/{id}")
    public ResponseEntity<OpcoesDePagamentoDTO> simularPagamento(@PathVariable String id) {
        OpcoesDePagamentoDTO pagamentoSimulado = service.simularPagamento(id);
        return ResponseEntity.ok(pagamentoSimulado);
    }
}