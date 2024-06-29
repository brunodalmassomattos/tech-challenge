package br.com.fiap.newparquimetro.controller;

import br.com.fiap.newparquimetro.domain.condutor.Condutor;
import br.com.fiap.newparquimetro.domain.condutor.Endereco;
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
        return ResponseEntity.ok(this.condutorService.save(this.prepararRequest(null, condutorRequestDTO)));
    }

    @PutMapping("/{idCondutor}")
    public ResponseEntity<CondutorResponseDTO> condutor(@PathVariable String idCondutor,
                                                        @Valid @RequestBody CondutorRequestDTO condutorRequestDTO) throws ParseException {
        return ResponseEntity.ok(this.condutorService.update(this.prepararRequest(idCondutor, condutorRequestDTO)));
    }

    @DeleteMapping("/{idCondutor}")
    public ResponseEntity<Void> condutor(@PathVariable String idCondutor) throws ParseException {
        this.condutorService.delete(Condutor.builder().id(idCondutor).build());
        return ResponseEntity.noContent().build();
    }

    private Condutor prepararRequest(String idCondutor, CondutorRequestDTO condutorRequestDTO) throws ParseException {
        return Condutor.builder()
                .id(idCondutor)
                .nome(condutorRequestDTO.nome())
                .cpfCnpj(condutorRequestDTO.cpfCnpj())
                .dataNascimento(CondutorRequestDTO.parseDate(condutorRequestDTO.dataNascimento()))
                .telefone(condutorRequestDTO.telefone())
                .idFormaPagamento(condutorRequestDTO.idFormaDePagamento())
                .endereco(Endereco.builder()
                        .logradouro(condutorRequestDTO.endereco().logradouro())
                        .numero(condutorRequestDTO.endereco().numero())
                        .complemento(condutorRequestDTO.endereco().complemento())
                        .bairro(condutorRequestDTO.endereco().bairro())
                        .cidade(condutorRequestDTO.endereco().cidade())
                        .cep(condutorRequestDTO.endereco().cep())
                        .estado(condutorRequestDTO.endereco().estado())
                        .build())
                .veiculos(VeiculoDTO.toVeiculos(condutorRequestDTO.veiculos()))
                .build();
    }

}
