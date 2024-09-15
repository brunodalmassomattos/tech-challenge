package br.com.fiap.level3.domain.restaurante.application;

import br.com.fiap.level3.domain.restaurante.core.model.restaurante.AlterarRestauranteDTO;
import br.com.fiap.level3.domain.restaurante.core.model.restaurante.Restaurante;
import br.com.fiap.level3.domain.restaurante.core.model.restaurante.RestauranteDTO;
import br.com.fiap.level3.domain.restaurante.core.model.tiporestaurante.TipoRestauranteDTO;
import br.com.fiap.level3.domain.restaurante.core.ports.incoming.AddRestaurante;
import br.com.fiap.level3.domain.restaurante.core.ports.incoming.AlterRestaurante;
import br.com.fiap.level3.domain.restaurante.core.ports.incoming.DeleteRestaurante;
import br.com.fiap.level3.domain.restaurante.core.ports.incoming.FindRestaurante;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/v1/restaurantes")
public class RestauranteController {

    @Qualifier("FindRestaurante")
    private final FindRestaurante findRestaurante;

    @Qualifier("AddRestaurante")
    private final AddRestaurante addRestaurante;

    @Qualifier("AlterRestaurante")
    private final AlterRestaurante alterRestaurante;

    @Qualifier("DeleteRestaurante")
    private final DeleteRestaurante deleteRestaurante;

    public RestauranteController(FindRestaurante findRestaurante,
                                 AddRestaurante addRestaurante,
                                 AlterRestaurante alterRestaurante,
                                 DeleteRestaurante deleteRestaurante) {
        this.findRestaurante = findRestaurante;
        this.addRestaurante = addRestaurante;
        this.alterRestaurante = alterRestaurante;
        this.deleteRestaurante = deleteRestaurante;
    }

    @GetMapping()
    public ResponseEntity<List<RestauranteDTO>> buscarRestaurante() {
        List<Restaurante> restaurantes = this.findRestaurante.getRestaurantes();
        if (restaurantes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(RestauranteDTO.fromRestaurantes(restaurantes));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestauranteDTO> buscarRestaurantePorId(@PathVariable String id) {
        Optional<Restaurante> restaurante = this.findRestaurante.getRestauranteById(UUID.fromString(id));
        return restaurante.map(value -> ResponseEntity.ok(RestauranteDTO.fromRestaurante(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/nome")
    public ResponseEntity<List<RestauranteDTO>> buscarRestaurantePorNome(@RequestParam String nome) {
        List<Restaurante> restaurantes = this.findRestaurante.getRestauranteByNome(nome);

        if (restaurantes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(RestauranteDTO.fromRestaurantes(restaurantes));
    }

    @GetMapping("/tipo-restaurante")
    public ResponseEntity<List<RestauranteDTO>> buscarRestaurantePorTipo(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String descricao) {

        List<Restaurante> restaurantes = this.findRestaurante.getRestaurantesByTipoRestaurante(
                TipoRestauranteDTO.toTipoRestaurante(id, descricao));

        if (restaurantes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(RestauranteDTO.fromRestaurantes(restaurantes));
    }

    @PostMapping
    public ResponseEntity<String> adicionarRestaurante(@RequestBody RestauranteDTO restaurante) {
        this.addRestaurante.save(RestauranteDTO.toRestaurante(null, restaurante));
        return new ResponseEntity<>("Novo Restaurante cadastrado", HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> alteraRestaurante(
            @PathVariable String id,
            @Valid @RequestBody AlterarRestauranteDTO restaurante) {
        this.alterRestaurante.alterRestaurante(AlterarRestauranteDTO.toRestaurante(id, restaurante));
        return new ResponseEntity<>("Restaurante Alterado", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletaRestaurante(@PathVariable String id) {
        this.deleteRestaurante.deleteRestaurante(UUID.fromString(id));
        return new ResponseEntity<>("Restaurante Alterado", HttpStatus.NO_CONTENT);
    }

}