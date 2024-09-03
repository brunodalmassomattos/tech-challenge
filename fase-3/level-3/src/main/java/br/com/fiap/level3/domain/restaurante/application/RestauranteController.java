package br.com.fiap.level3.domain.restaurante.application;

import br.com.fiap.level3.domain.restaurante.core.domain.model.restaurante.Restaurante;
import br.com.fiap.level3.domain.restaurante.core.domain.model.restaurante.RestauranteDTO;
import br.com.fiap.level3.domain.restaurante.core.ports.incoming.AddRestaurante;
import br.com.fiap.level3.domain.restaurante.core.ports.incoming.FindRestaurante;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Qualifier("FindRestaurante")
    private final FindRestaurante findRestaurante;

    @Qualifier("AddRestaurante")
    private final AddRestaurante addRestaurante;

    public RestauranteController(FindRestaurante findByIDRestaurante, AddRestaurante addRestaurante) {
        this.findRestaurante = findByIDRestaurante;
        this.addRestaurante = addRestaurante;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestauranteDTO> buscarRestaurantePorId(@PathVariable String id) {
        Optional<Restaurante> restaurante = this.findRestaurante.getRestauranteById(UUID.fromString(id));

        if (restaurante.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(RestauranteDTO.fromRestaurante(restaurante));
    }

    @GetMapping
    public ResponseEntity<List<RestauranteDTO>> buscarRestaurantePorNome(@RequestParam String nome) {
        List<Restaurante> restaurantes = this.findRestaurante.getRestauranteByNome(nome);

        if (restaurantes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(RestauranteDTO.fromRestaurantes(restaurantes));
    }

    @GetMapping("/all")
    public ResponseEntity<List<RestauranteDTO>> buscarRestaurante() {
        List<Restaurante> restaurantes = this.findRestaurante.getRestaurantes();
        if (restaurantes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(RestauranteDTO.fromRestaurantes(restaurantes));
    }

    @PostMapping
    public ResponseEntity<String> adicionarRestaurante(@RequestBody RestauranteDTO restaurante) {
        this.addRestaurante.save(RestauranteDTO.toRestaurante(restaurante));
        return new ResponseEntity<>("Novo Restaurante cadastrado", HttpStatus.CREATED);
    }
}