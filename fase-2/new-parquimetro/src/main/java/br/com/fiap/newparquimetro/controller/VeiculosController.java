package br.com.fiap.newparquimetro.controller;

import br.com.fiap.newparquimetro.domain.veiculo.VeiculoJava;
import br.com.fiap.newparquimetro.dto.veiculos.AtualizaVeiculoDTO;
import br.com.fiap.newparquimetro.dto.veiculos.CadastraVeiculoDTO;
import br.com.fiap.newparquimetro.dto.veiculos.ListaVeiculoDTO;
import br.com.fiap.newparquimetro.dto.veiculos.VeiculoResponseDTO;
import br.com.fiap.newparquimetro.service.VeiculoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("/veiculos")
public class VeiculosController {

    @Autowired
    private VeiculoService veiculoService;

    @PostMapping("/condutores/{idCondutor}")
    public ResponseEntity<VeiculoResponseDTO> cadastrarVeiculo(@PathVariable String idCondutor, @RequestBody @Valid CadastraVeiculoDTO dado, UriComponentsBuilder uriBuilder) {
        VeiculoJava veiculo = veiculoService.cadastrarVeiculo(idCondutor, dado);
        var uri = uriBuilder.path("/veiculos/{id}").buildAndExpand(veiculo.getId()).toUri();
        return ResponseEntity.created(uri).body(new VeiculoResponseDTO(veiculo));
    }

    @GetMapping
    public Page<ListaVeiculoDTO> listar(Pageable paginacao) {
        return veiculoService.listar(paginacao).map(ListaVeiculoDTO::new);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListaVeiculoDTO> buscarPorId(@PathVariable String id) {
        Optional<VeiculoJava> veiculo = veiculoService.buscarPorId(id);
        return veiculo.map(value -> ResponseEntity.ok(new ListaVeiculoDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<VeiculoResponseDTO> atualizar(@PathVariable String id, @RequestBody @Valid AtualizaVeiculoDTO dado) {
        VeiculoJava veiculoAtualizado = veiculoService.atualizar(id, dado);
        return ResponseEntity.ok(new VeiculoResponseDTO(veiculoAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable String id) {
        veiculoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
