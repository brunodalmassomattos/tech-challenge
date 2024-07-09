package br.com.fiap.newparquimetro.controller;

import br.com.fiap.newparquimetro.dto.tarifa.TarifaRequestDTO;
import br.com.fiap.newparquimetro.dto.tarifa.TarifaResponseDTO;
import br.com.fiap.newparquimetro.service.TarifaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarifa")
public class TarifaController {

    @Autowired
    private TarifaService tarifaService;

    @PostMapping
    public ResponseEntity<TarifaResponseDTO> createTarifa(@Valid @RequestBody TarifaRequestDTO tarifaDto) {
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(tarifaService.save(tarifaDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarifaResponseDTO> getTarifa(@PathVariable String id) {
        return ResponseEntity.ok(tarifaService.get(id));
    }

    @GetMapping()
    public ResponseEntity<List<TarifaResponseDTO>> getAllTarifas() {
        return ResponseEntity.ok(tarifaService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarifaResponseDTO> updateTarifa(@PathVariable String id,
                                                          @RequestBody TarifaRequestDTO tarifaDto) {
        return ResponseEntity.ok(tarifaService.update(id, tarifaDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTarifa(@PathVariable String id) {
        tarifaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
