package br.com.fiap.newparquimetro.controller;

import br.com.fiap.newparquimetro.domain.veiculo.VeiculoJava;
import br.com.fiap.newparquimetro.dto.AtualizaVeiculoDTO;
import br.com.fiap.newparquimetro.dto.CadastraVeiculoDTO;
import br.com.fiap.newparquimetro.dto.ListaVeiculoDTO;
import br.com.fiap.newparquimetro.service.VeiculoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/veiculos")
public class VeiculosController {

    @Autowired
    private VeiculoService veiculoService;

    @PostMapping
    public ResponseEntity<Void> cadastrarVeiculo(@RequestBody @Valid CadastraVeiculoDTO dado) {
        veiculoService.cadastrarVeiculo(dado);
        return ResponseEntity.ok().build();
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

    @PutMapping
    public ResponseEntity<Void> atualizar(@RequestBody @Valid AtualizaVeiculoDTO dado) {
        veiculoService.atualizar(dado);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable String id) {
        veiculoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
