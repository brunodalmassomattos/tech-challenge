package br.com.fiap.msgerenciamentocliente.controller;

import br.com.fiap.msgerenciamentocliente.model.Endereco;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/enderecos")
public class EnderecoController {

    private List<Endereco> enderecos = new ArrayList<>();

    @PostMapping
    public ResponseEntity<Endereco> criarEndereco(@RequestBody Endereco endereco) {
        // Gerando um novo UUID caso não tenha sido fornecido
        if (endereco.getId() == null) {
            endereco = new Endereco(
                    UUID.randomUUID(),
                    endereco.getRua(),
                    endereco.getBairro(),
                    endereco.getComplemento(),
                    endereco.getNumero(),
                    endereco.getCidade(),
                    endereco.getEstado(),
                    endereco.getCep()
            );
        }
        enderecos.add(endereco);
        return new ResponseEntity<>(endereco, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Endereco>> listarEnderecos() {
        return ResponseEntity.ok(enderecos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Endereco> buscarEnderecoPorId(@PathVariable UUID id) {
        return enderecos.stream()
                .filter(endereco -> endereco.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Endereco> atualizarEndereco(
            @PathVariable UUID id,
            @RequestBody Endereco enderecoAtualizado) {
        for (int i = 0; i < enderecos.size(); i++) {
            if (enderecos.get(i).getId().equals(id)) {
                // Mantém o ID original
                enderecoAtualizado = new Endereco(
                        id,
                        enderecoAtualizado.getRua(),
                        enderecoAtualizado.getBairro(),
                        enderecoAtualizado.getComplemento(),
                        enderecoAtualizado.getNumero(),
                        enderecoAtualizado.getCidade(),
                        enderecoAtualizado.getEstado(),
                        enderecoAtualizado.getCep()
                );
                enderecos.set(i, enderecoAtualizado);
                return ResponseEntity.ok(enderecoAtualizado);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEndereco(@PathVariable UUID id) {
        boolean removido = enderecos.removeIf(endereco -> endereco.getId().equals(id));
        return removido ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }

    @GetMapping("/busca/cep/{cep}")
    public ResponseEntity<List<Endereco>> buscarEnderecosPorCep(@PathVariable String cep) {
        List<Endereco> enderecosEncontrados = enderecos.stream()
                .filter(endereco -> endereco.getCep().equals(cep))
                .toList();
        return ResponseEntity.ok(enderecosEncontrados);
    }

    @GetMapping("/busca")
    public ResponseEntity<List<Endereco>> buscarEnderecos(
            @RequestParam(required = false) String cidade,
            @RequestParam(required = false) String estado) {
        List<Endereco> enderecosEncontrados = enderecos.stream()
                .filter(endereco ->
                        (cidade == null || endereco.getCidade().toLowerCase().contains(cidade.toLowerCase())) &&
                                (estado == null || endereco.getEstado().toLowerCase().contains(estado.toLowerCase())))
                .toList();
        return ResponseEntity.ok(enderecosEncontrados);
    }
}