package br.com.fiap.newparquimetro.controller;

import br.com.fiap.newparquimetro.domain.condutor.Condutor;
import br.com.fiap.newparquimetro.domain.opcoesDePagamento.OpcoesDePagamento;
import br.com.fiap.newparquimetro.dto.opcaopagamentos.CriarOpcaoPagamentoDTO;
import br.com.fiap.newparquimetro.dto.opcaopagamentos.OpcoesDePagamentoDTO;
import br.com.fiap.newparquimetro.service.OpcoesDePagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/opcoes-pagamento")
public class OpcoesDePagamentoController {

    @Autowired
    private OpcoesDePagamentoService service;

    @PostMapping
    @Transactional
    public ResponseEntity<OpcoesDePagamentoDTO> criarOpcaoPagamento(@RequestBody @Valid CriarOpcaoPagamentoDTO dados) {
        OpcoesDePagamentoDTO salva = service.save(OpcoesDePagamento.builder()
                .idTempo(dados.getIdTempo())
                .dataPagamento(dados.getDataPagamento())
                .status(dados.getStatus())
                .valor(dados.getValor())
                .condutor(Condutor.builder().id(dados.getIdCondutor()).build())
                .build());
        return ResponseEntity.ok(salva);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OpcoesDePagamentoDTO> buscarPorIdCondutor(@PathVariable String id) {
        OpcoesDePagamento opcao = service.findById(id);
        return ResponseEntity.ok(OpcoesDePagamentoDTO.toDTO(opcao));
    }

    @GetMapping("/by-condutor/{condutorId}")
    public ResponseEntity<List<OpcoesDePagamentoDTO>> listarPorCondutorId(@PathVariable String condutorId) {
        List<OpcoesDePagamentoDTO> pagamentos = service.findAllByCondutorId(condutorId);
        return ResponseEntity.ok(pagamentos);
    }

    @GetMapping()
    public ResponseEntity<List<OpcoesDePagamentoDTO>> getAllPagamentos() {
        return ResponseEntity.ok(service.getAll());
    }
}