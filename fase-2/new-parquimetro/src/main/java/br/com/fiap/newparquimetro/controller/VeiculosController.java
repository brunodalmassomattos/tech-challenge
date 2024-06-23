package br.com.fiap.newparquimetro.controller;

import br.com.fiap.newparquimetro.domain.veiculo.VeiculoJava;
import br.com.fiap.newparquimetro.dto.AtualizaVeiculoDTO;
import br.com.fiap.newparquimetro.dto.CadastraVeiculoDTO;
import br.com.fiap.newparquimetro.dto.ListaVeiculoDTO;
import br.com.fiap.newparquimetro.repositories.VeiculoRepository;
import jakarta.transaction.Transactional;
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
    private VeiculoRepository veiculoRepository;

    @PostMapping
    @Transactional
    public void cadastrarVeiculo(@RequestBody @Valid CadastraVeiculoDTO dado){
        VeiculoJava veiculo = new VeiculoJava(dado);
        veiculoRepository.save(veiculo);
    }

    @GetMapping
    public Page<ListaVeiculoDTO> listar(Pageable paginacao){
        return veiculoRepository.findAll(paginacao).map(ListaVeiculoDTO::new);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListaVeiculoDTO> buscarPorId(@PathVariable Long id) {
        Optional<VeiculoJava> veiculo = veiculoRepository.findById(id);
        return veiculo.map(value -> ResponseEntity.ok(new ListaVeiculoDTO(value))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid AtualizaVeiculoDTO dado){
        VeiculoJava veiculo = veiculoRepository.getReferenceById(dado.id());
        veiculo.atualizarInformacoes(dado);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id){
        veiculoRepository.deleteById(id);
    }
}
