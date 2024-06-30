package br.com.fiap.newparquimetro.controller;

import br.com.fiap.newparquimetro.dto.TarifaDTO;
import br.com.fiap.newparquimetro.service.TarifaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tarifa")
public class TarifaController {

    @Autowired
    private TarifaService tarifaService;

    @PostMapping
    public ResponseEntity<TarifaDTO> createTarifa(@RequestBody TarifaDTO tarifaDTO) {
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(tarifaService.save(tarifaDTO));
    }
}
