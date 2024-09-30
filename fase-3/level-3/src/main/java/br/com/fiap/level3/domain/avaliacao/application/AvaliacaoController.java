package br.com.fiap.level3.domain.avaliacao.application;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.level3.domain.avaliacao.core.domain.model.avaliacao.AlterarAvaliacaoDTO;
import br.com.fiap.level3.domain.avaliacao.core.domain.model.avaliacao.Avaliacao;
import br.com.fiap.level3.domain.avaliacao.core.domain.model.avaliacao.AvaliacaoDTO;
import br.com.fiap.level3.domain.avaliacao.core.ports.incoming.AddAvaliacao;
import br.com.fiap.level3.domain.avaliacao.core.ports.incoming.AlterAvaliacao;
import br.com.fiap.level3.domain.avaliacao.core.ports.incoming.FindAvaliacao;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/avaliacao")
public class AvaliacaoController {

    @Qualifier("FindAvaliacao")
    private final FindAvaliacao findAvaliacao;

    @Qualifier("AddAvaliacao")
    private final AddAvaliacao addAvaliacao;

    @Qualifier("AlterAvaliacao")
    private final AlterAvaliacao alterAvaliacao;
	
	public AvaliacaoController(FindAvaliacao findAvaliacao, AddAvaliacao addAvaliacao, AlterAvaliacao alterAvaliacao) {
		this.findAvaliacao = findAvaliacao;
		this.addAvaliacao = addAvaliacao;
		this.alterAvaliacao = alterAvaliacao;
	}
    
    @PostMapping
    public ResponseEntity<String> adicionarAvaliacao(@RequestBody AvaliacaoDTO avaliacao) {
        this.addAvaliacao.save(AvaliacaoDTO.toAvaliacao(null, avaliacao));
        return new ResponseEntity<>("Nova Avaliacao cadastrada", HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoDTO> buscarAvaliacaoPorId(@PathVariable String id) {
        Optional<Avaliacao> avaliacao = this.findAvaliacao.getAvaliacaoById(UUID.fromString(id));
        return avaliacao.map(value -> ResponseEntity.ok(AvaliacaoDTO.fromAvaliacao(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @GetMapping("/restauranteId")
    public ResponseEntity<List<AvaliacaoDTO>> buscarAvaliacaoPorRestauranteId(@RequestParam String restauranteId) {
        List<Optional<Avaliacao>> avaliacoes = this.findAvaliacao.getAvaliacaoByRestauranteId(UUID.fromString(restauranteId));

        List<Avaliacao> avaliacoesValidas = avaliacoes.stream()
            .flatMap(Optional::stream)
            .collect(Collectors.toList());

        if (avaliacoesValidas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(AvaliacaoDTO.fromAvaliacoes(avaliacoesValidas));
    }
    
    @GetMapping("/usuarioId")
    public ResponseEntity<List<AvaliacaoDTO>> buscarAvaliacaoPorUsuarioId(@RequestParam String usuarioId) {
        List<Optional<Avaliacao>> avaliacoes = this.findAvaliacao.getAvaliacaoByUsuarioId(UUID.fromString(usuarioId));

        List<Avaliacao> avaliacoesValidas = avaliacoes.stream()
            .flatMap(Optional::stream)
            .collect(Collectors.toList());

        if (avaliacoesValidas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(AvaliacaoDTO.fromAvaliacoes(avaliacoesValidas));
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<String> alteraAvaliacao(@PathVariable String id, @Valid @RequestBody AlterarAvaliacaoDTO alterarAvaliacaoDTO){
		this.alterAvaliacao.alterAvaliacao(AlterarAvaliacaoDTO.toAvaliacao(id, alterarAvaliacaoDTO));
        return new ResponseEntity<>("Avaliacao Alterada", HttpStatus.OK);
    	
    }
	
}
