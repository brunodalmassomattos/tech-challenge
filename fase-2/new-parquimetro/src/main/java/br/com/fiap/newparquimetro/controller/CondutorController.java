package br.com.fiap.newparquimetro.controller;

import br.com.fiap.newparquimetro.domain.condutor.Endereco;
import br.com.fiap.newparquimetro.dto.CondutorRequestDTO;
import br.com.fiap.newparquimetro.dto.CondutorResponseDTO;
import br.com.fiap.newparquimetro.domain.condutor.Condutor;
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

    private final CondutorService userService;

    @PostMapping
    public ResponseEntity<CondutorResponseDTO> user(@Valid @RequestBody CondutorRequestDTO condutorRequestDTO) throws ParseException {
        return ResponseEntity.ok(this.userService.save(this.prepararRequest(condutorRequestDTO)));
    }

    private Condutor prepararRequest(CondutorRequestDTO condutorRequestDTO) throws ParseException {
        return Condutor.builder()
                .nome(condutorRequestDTO.nome())
                .cpfCnpj(condutorRequestDTO.cpfCnpj())
                .dataNascimento(CondutorRequestDTO.parseDate(condutorRequestDTO.dataNascimento()))
                .telefone(condutorRequestDTO.telefone())
                .endereco(Endereco.builder()
                        .logradouro(condutorRequestDTO.endereco().logradouro())
                        .numero(condutorRequestDTO.endereco().numero())
                        .complemento(condutorRequestDTO.endereco().complemento())
                        .bairro(condutorRequestDTO.endereco().bairro())
                        .cidade(condutorRequestDTO.endereco().cidade())
                        .cep(condutorRequestDTO.endereco().cep())
                        .build())
                .veiculos(VeiculoDTO.toVeiculos(condutorRequestDTO.veiculos()))
                .build();
    }

}
