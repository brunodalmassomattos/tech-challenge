package br.com.fiap.level3.domain.restaurante.application.controller;

import br.com.fiap.level3.domain.restaurante.core.domain.model.restaurante.Restaurante;
import br.com.fiap.level3.domain.restaurante.core.domain.model.restaurante.RestauranteDTO;
import br.com.fiap.level3.domain.restaurante.core.ports.incoming.FindByIdRestaurante;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/restaurantes")
@RequiredArgsConstructor
public class RestauranteController {

    @Qualifier("FindByIdRestaurante")
    private final FindByIdRestaurante findByIDRestaurante;

    @GetMapping("/{id}")
    public ResponseEntity<RestauranteDTO> buscarRestaurantePorId(@PathVariable String id) {
        Optional<Restaurante> restauranteById = this.findByIDRestaurante.getRestauranteById(UUID.fromString(id));

        if (restauranteById.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(RestauranteDTO.fromRestaurante(restauranteById));
    }
}