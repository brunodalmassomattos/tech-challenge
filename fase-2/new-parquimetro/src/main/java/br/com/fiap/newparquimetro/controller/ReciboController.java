package br.com.fiap.newparquimetro.controller;

import br.com.fiap.newparquimetro.dto.recibos.ReciboResponseDTO;
import br.com.fiap.newparquimetro.service.ReciboService;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @GetMapping()
    public ResponseEntity<List<ReciboResponseDTO>> getRecibo(@RequestParam String idCondutor,
                                                             @Schema(type = "string", format = "date", example = "2023-06-27")
                                                             @RequestParam String dataInicial,
                                                             @Schema(type = "string", format = "date", example = "2023-06-27")
                                                             @RequestParam String dataFinal) {
        return ResponseEntity.ok(reciboService.emitirRecibo(idCondutor, dataInicial, dataFinal));
    }

    @PostMapping()
    public ResponseEntity<ReciboResponseDTO> saveRecibo(@RequestHeader String idPagamento) {
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(reciboService.gerarRecibo(idPagamento));
    }
}
