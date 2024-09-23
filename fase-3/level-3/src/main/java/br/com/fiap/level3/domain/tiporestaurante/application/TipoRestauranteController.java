package br.com.fiap.level3.domain.tiporestaurante.application;

import br.com.fiap.level3.domain.tiporestaurante.core.model.TipoRestauranteDTO;
import br.com.fiap.level3.domain.tiporestaurante.core.ports.incoming.AddTipoRestaurante;
import br.com.fiap.level3.domain.tiporestaurante.core.ports.incoming.AlterTipoRestaurante;
import br.com.fiap.level3.domain.tiporestaurante.core.ports.incoming.DeleteTipoRestaurante;
import br.com.fiap.level3.domain.tiporestaurante.core.ports.incoming.FindTipoRestaurante;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/tipo-restaurante")
@RequiredArgsConstructor
public class TipoRestauranteController {

    private final AddTipoRestaurante addTipoRestaurante;
    private final AlterTipoRestaurante alterTipoRestaurante;
    private final DeleteTipoRestaurante deleteTipoRestaurante;
    private final FindTipoRestaurante findTipoRestaurante;

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

    @GetMapping("")
    public ResponseEntity<List<TipoRestauranteDTO>> listaTodosTipoRestaurante() {
        var tipoRestaurantes = this.findTipoRestaurante.getTipoRestaurantes();

        if (tipoRestaurantes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(TipoRestauranteDTO.fromTipoRestaurante(tipoRestaurantes));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoRestauranteDTO> buscarTipoRestaurantePorID(@PathVariable UUID id) {
        var tipoRestaurante = this.findTipoRestaurante.getTipoRestauranteById(id);

        return tipoRestaurante
                .map(restaurante -> ResponseEntity.ok(TipoRestauranteDTO.fromDTO(restaurante)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/descricao")
    public ResponseEntity<List<TipoRestauranteDTO>> listaPorTipoRestaurante(@RequestParam String descricao) {
        var tipoRestaurantes = this.findTipoRestaurante.getTipoRestauranteByDescricao(descricao);

        if (tipoRestaurantes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(TipoRestauranteDTO.fromTipoRestaurante(tipoRestaurantes));
    }

}
