package br.com.fiap.newparquimetro.controller;

import br.com.fiap.newparquimetro.domain.condutor.Condutor;
import br.com.fiap.newparquimetro.domain.condutor.Endereco;
import br.com.fiap.newparquimetro.dto.AlteracaoCondutorRequestDTO;
import br.com.fiap.newparquimetro.dto.CondutorRequestDTO;
import br.com.fiap.newparquimetro.dto.CondutorResponseDTO;
import br.com.fiap.newparquimetro.dto.VeiculoDTO;
import br.com.fiap.newparquimetro.service.CondutorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/condutor")
@AllArgsConstructor
public class CondutorController {

    private final CondutorService condutorService;

    @PostMapping
    public ResponseEntity<CondutorResponseDTO> condutor(@Valid @RequestBody CondutorRequestDTO condutorRequestDTO) throws ParseException {
        return ResponseEntity.ok(this.condutorService.save(CondutorRequestDTO.prepararRequest(null, condutorRequestDTO) ));
    }

    @GetMapping("/{idCondutor}")
    public ResponseEntity<CondutorResponseDTO> buscarCondutor(@PathVariable String idCondutor) throws ParseException {
        return ResponseEntity.ok(this.condutorService.find(idCondutor));
    }

    @PutMapping("/{idCondutor}")
    public ResponseEntity<CondutorResponseDTO> alterarCondutor(@PathVariable String idCondutor,
                                                        @Valid @RequestBody AlteracaoCondutorRequestDTO condutorRequestDTO) throws ParseException {
        return ResponseEntity.ok(this.condutorService.update(AlteracaoCondutorRequestDTO.prepararRequest(idCondutor, condutorRequestDTO)));
    }

    @DeleteMapping("/{idCondutor}")
    public ResponseEntity<Void> deletarCondutor(@PathVariable String idCondutor) throws ParseException {
        this.condutorService.delete(Condutor.builder().id(idCondutor).build());
        return ResponseEntity.noContent().build();
    }

}
