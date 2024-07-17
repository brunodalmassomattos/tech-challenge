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

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/veiculos")
public class VeiculosController {

    @Autowired
    private VeiculoService veiculoService;


    @PostMapping("/condutores/{idCondutor}")
    public ResponseEntity<List<VeiculoResponseDTO>> cadastrarVeiculo(@PathVariable String idCondutor, @RequestBody @Valid List<CadastraVeiculoDTO> dados, UriComponentsBuilder uriBuilder) {
        try {
            List<VeiculoJava> veiculos = veiculoService.cadastrarVeiculos(dados, idCondutor);
            List<VeiculoResponseDTO> responseDTOs = veiculos.stream()
                    .map(VeiculoResponseDTO::new)
                    .collect(Collectors.toList());

            // Construindo URI usando a URI do primeiro veículo cadastrado (apenas como exemplo)
            URI uri = uriBuilder.path("/veiculos/{id}").buildAndExpand(veiculos.get(0).getId()).toUri();

            return ResponseEntity.created(uri).body(responseDTOs);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
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
