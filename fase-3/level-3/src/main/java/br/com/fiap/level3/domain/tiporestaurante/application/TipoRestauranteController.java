package br.com.fiap.level3.domain.tiporestaurante.application;

import br.com.fiap.level3.domain.tiporestaurante.core.model.TipoRestauranteDTO;
import br.com.fiap.level3.domain.tiporestaurante.core.ports.incoming.AddTipoRestaurante;
import br.com.fiap.level3.domain.tiporestaurante.core.ports.incoming.AlterTipoRestaurante;
import br.com.fiap.level3.domain.tiporestaurante.core.ports.incoming.DeleteTipoRestaurante;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/tipo-restaurante")
@RequiredArgsConstructor
public class TipoRestauranteController {

    private final AddTipoRestaurante addTipoRestaurante;
    private final AlterTipoRestaurante alterTipoRestaurante;
    private final DeleteTipoRestaurante deleteTipoRestaurante;

    @PostMapping("")
    public ResponseEntity<String> adicionaTipoRestaurante(@Valid @RequestBody TipoRestauranteDTO tipoRestauranteDTO) {

        this.addTipoRestaurante.handle(tipoRestauranteDTO);
        return new ResponseEntity<>("Novo Tipo de Restaurante foi incluido!", HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> alteraTipoRestaurante(
            @Valid @PathVariable String id,
            @Valid @RequestBody TipoRestauranteDTO tipoRestauranteDTO) {

        this.alterTipoRestaurante.alterData(id, tipoRestauranteDTO);
        return new ResponseEntity<>("Tipo de Restaurante foi alterado!", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> alteraTipoRestaurante(@Valid @PathVariable String id) {

        this.deleteTipoRestaurante.handle(id);
        return new ResponseEntity<>("Tipo de Restaurante foi alterado!", HttpStatus.NO_CONTENT);
    }
}
