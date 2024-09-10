package br.com.fiap.level3.domain.tiporestaurante.application;

import br.com.fiap.level3.domain.tiporestaurante.core.model.TipoRestauranteDTO;
import br.com.fiap.level3.domain.tiporestaurante.core.ports.incoming.AddTipoRestaurante;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tipo-restaurante/v1")
@RequiredArgsConstructor
public class TipoRestauranteController {

    private final AddTipoRestaurante addTipoRestaurante;

    @PostMapping("")
    public ResponseEntity<String> adicionaRestaurante(@Valid @RequestBody TipoRestauranteDTO tipoRestauranteDTO){
        addTipoRestaurante.handle(tipoRestauranteDTO);
        return new ResponseEntity<>("Novo Tipo de Restaurante foi incluido!", HttpStatus.CREATED);
    }
}
