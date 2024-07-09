package br.com.fiap.newparquimetro.controller;

import br.com.fiap.newparquimetro.dto.recibos.ReciboResponseDTO;
import br.com.fiap.newparquimetro.service.ReciboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emissao-recibo")
public class ReciboController {

    @Autowired
    private ReciboService reciboService;

    @GetMapping("/{idCondutor}")
    public ResponseEntity<List<ReciboResponseDTO>> getRecibo(@RequestHeader String idCondutor) {
        return ResponseEntity.ok(reciboService.emitirRecibo(idCondutor));
    }

    @PostMapping()
    public ResponseEntity<List<ReciboResponseDTO>> saveRecibo(@RequestHeader String idCondutor,
                                                              @RequestHeader String idTarifa) {
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(reciboService.save(idCondutor, idTarifa));
    }
}
