package br.com.fiap.newparquimetro.controller;

import br.com.fiap.newparquimetro.domain.opcoesDePagamento.OpcoesDePagamento;
import br.com.fiap.newparquimetro.dto.AtualizarOpcaoPagamentoDTO;
import br.com.fiap.newparquimetro.dto.CriarOpcaoPagamentoDTO;
import br.com.fiap.newparquimetro.dto.OpcoesDePagamentoDTO;
import br.com.fiap.newparquimetro.repositories.OpcoesDePagamentoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/opcoes-pagamento")
public class OpcoesDePagamentoController {

    @Autowired
    private OpcoesDePagamentoRepository opcoesDePagamentoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<OpcoesDePagamentoDTO> criarOpcaoPagamento(@RequestBody @Valid CriarOpcaoPagamentoDTO dados) {
        OpcoesDePagamento novaOpcao = new OpcoesDePagamento(dados);
        OpcoesDePagamento salva = opcoesDePagamentoRepository.save(novaOpcao);
        return ResponseEntity.ok(new OpcoesDePagamentoDTO(salva));
    }

    @GetMapping
    public Page<OpcoesDePagamentoDTO> listarOpcoes(Pageable paginacao) {
        return opcoesDePagamentoRepository.findAll(paginacao).map(OpcoesDePagamentoDTO::new);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OpcoesDePagamentoDTO> buscarPorId(@PathVariable String id) {
        Optional<OpcoesDePagamento> opcao = opcoesDePagamentoRepository.findById(id);
        return opcao.map(value -> ResponseEntity.ok(new OpcoesDePagamentoDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<OpcoesDePagamentoDTO> atualizarOpcao(@PathVariable String id, @RequestBody @Valid AtualizarOpcaoPagamentoDTO dados) {
        OpcoesDePagamento opcaoExistente = opcoesDePagamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Opção de pagamento não encontrada"));

        opcaoExistente.atualizarDados(dados);
        opcoesDePagamentoRepository.save(opcaoExistente);
        return ResponseEntity.ok(new OpcoesDePagamentoDTO(opcaoExistente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluirOpcao(@PathVariable String id) {
        opcoesDePagamentoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
