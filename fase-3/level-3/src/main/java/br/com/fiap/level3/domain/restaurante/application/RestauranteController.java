package br.com.fiap.level3.domain.restaurante.application;

import br.com.fiap.level3.domain.exception.ControllerNotFoundException;
import br.com.fiap.level3.domain.restaurante.core.model.endereco.AlterarEnderecoDTO;
import br.com.fiap.level3.domain.restaurante.core.model.endereco.Endereco;
import br.com.fiap.level3.domain.restaurante.core.model.restaurante.AlterarRestauranteDTO;
import br.com.fiap.level3.domain.restaurante.core.model.restaurante.Restaurante;
import br.com.fiap.level3.domain.restaurante.core.model.restaurante.RestauranteDTO;
import br.com.fiap.level3.domain.restaurante.core.model.tiporestaurante.TipoRestauranteDTO;
import br.com.fiap.level3.domain.restaurante.core.ports.incoming.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/v1/restaurantes",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController {

    @Qualifier("FindRestaurante")
    private final FindRestaurante findRestaurante;
    @Qualifier("AddRestaurante")
    private final AddRestaurante addRestaurante;
    @Qualifier("AlterRestaurante")
    private final AlterRestaurante alterRestaurante;
    @Qualifier("DeleteRestaurante")
    private final DeleteRestaurante deleteRestaurante;
    @Qualifier("AlterEndereco")
    private final AlterEndereco alterEndereco;

    public RestauranteController(FindRestaurante findRestaurante,
                                 AddRestaurante addRestaurante,
                                 AlterRestaurante alterRestaurante,
                                 DeleteRestaurante deleteRestaurante, AlterEndereco alterEndereco) {
        this.findRestaurante = findRestaurante;
        this.addRestaurante = addRestaurante;
        this.alterRestaurante = alterRestaurante;
        this.deleteRestaurante = deleteRestaurante;
        this.alterEndereco = alterEndereco;
    }

    @GetMapping()
    public ResponseEntity<List<RestauranteDTO>> buscarRestaurante() {
        List<Restaurante> restaurantes = this.findRestaurante.getRestaurantes();
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
        return new ResponseEntity<>("Restaurante Alterado", HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletaRestaurante(@PathVariable String id) {
        this.deleteRestaurante.deleteRestaurante(UUID.fromString(id));
        return new ResponseEntity<>("Restaurante Alterado", HttpStatus.OK);
    }

    @PatchMapping("/{idRestaurante}/endereco/{idEndereco}")
    public ResponseEntity<String> alteraEndereco(
            @PathVariable String idRestaurante,
            @PathVariable String idEndereco,
            @Valid @RequestBody AlterarEnderecoDTO enderecoDTO) {
        try {
            Endereco endereco = AlterarEnderecoDTO.toEndereco(idEndereco, enderecoDTO);
            this.alterEndereco.alterEndereco(UUID.fromString(idRestaurante), UUID.fromString(idEndereco), endereco);
            return new ResponseEntity<>("Endereço atualizado com sucesso", HttpStatus.OK);
        } catch (ControllerNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao atualizar o endereço", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}