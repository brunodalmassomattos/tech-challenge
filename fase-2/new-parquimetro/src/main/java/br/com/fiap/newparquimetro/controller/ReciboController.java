package br.com.fiap.newparquimetro.controller;

import br.com.fiap.newparquimetro.dto.ReciboDTO;
import br.com.fiap.newparquimetro.dto.ReciboRequestDTO;
import br.com.fiap.newparquimetro.service.ReciboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emissao-recibo")
public class ReciboController {

    @Autowired
    private ReciboService reciboService;

    @GetMapping("/{condutorId}")
    public ResponseEntity<ReciboDTO> getRecibo(@PathVariable String condutorId) {
        return ResponseEntity.ok(reciboService.emitirRecibo(condutorId));
    }

    @PostMapping()
    public ResponseEntity<ReciboDTO> saveRecibo(@RequestBody ReciboRequestDTO reciboDTO) {
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(reciboService.save(reciboDTO));
    }
}
