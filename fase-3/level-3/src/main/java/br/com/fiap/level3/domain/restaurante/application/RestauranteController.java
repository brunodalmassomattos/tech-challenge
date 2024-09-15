package br.com.fiap.level3.domain.restaurante.application;

import br.com.fiap.level3.domain.exception.ControllerNotFoundException;
import br.com.fiap.level3.domain.restaurante.core.EnderecoFacade;
import br.com.fiap.level3.domain.restaurante.core.domain.model.endereco.AlterarEnderecoDTO;
import br.com.fiap.level3.domain.restaurante.core.domain.model.endereco.Endereco;
import br.com.fiap.level3.domain.restaurante.core.domain.model.endereco.EnderecoDTO;
import br.com.fiap.level3.domain.restaurante.core.domain.model.restaurante.AlterarRestauranteDTO;
import br.com.fiap.level3.domain.restaurante.core.domain.model.restaurante.Restaurante;
import br.com.fiap.level3.domain.restaurante.core.domain.model.restaurante.RestauranteDTO;
import br.com.fiap.level3.domain.restaurante.core.domain.model.tiporestaurante.TipoRestaurante;
import br.com.fiap.level3.domain.restaurante.core.ports.incoming.*;

import jakarta.validation.Valid;
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
    @Qualifier("AlterRestaurante")
    private final AlterRestaurante alterRestaurante;
    @Qualifier("AlterEndereco")
    private final AlterEndereco alterEndereco;
    @Qualifier("AlterTipoRestaurante")
    private final AlterTipoRestaurante alterTipoRestaurante;


    public RestauranteController(FindRestaurante findRestaurante,
                                 AddRestaurante addRestaurante,
                                 AlterRestaurante alterRestaurante, AlterEndereco alterEndereco, AlterTipoRestaurante alterTipoRestaurante) {
        this.findRestaurante = findRestaurante;
        this.addRestaurante = addRestaurante;
        this.alterRestaurante = alterRestaurante;
        this.alterEndereco = alterEndereco;
        this.alterTipoRestaurante = alterTipoRestaurante;
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
    @PatchMapping("/{idRestaurante}/endereco/{idEndereco}")
    public ResponseEntity<String> alteraEndereco(
            @PathVariable String idRestaurante,
            @PathVariable String idEndereco,
            @Valid @RequestBody AlterarEnderecoDTO enderecoDTO) {
        try {
            Endereco endereco = AlterarEnderecoDTO.toEndereco(idEndereco, enderecoDTO);

            this.alterEndereco.alterEndereco(UUID.fromString(idEndereco), endereco);

            return new ResponseEntity<>("Endereço atualizado com sucesso", HttpStatus.OK);
        } catch (ControllerNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao atualizar o endereço", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PatchMapping("/{idRestaurante}/tipo/{idTipoRestaurante}")
    public ResponseEntity<String> alteraTipoRestaurante(
            @PathVariable String idRestaurante,
            @PathVariable String idTipoRestaurante,
            @Valid @RequestBody AlterarTipoRestauranteDTO tipoRestauranteDTO) {
        try {
            TipoRestaurante tipoRestaurante = AlterarTipoRestauranteDTO.toTipoRestaurante(idTipoRestaurante, tipoRestauranteDTO);
            this.alterTipoRestaurante.alterTipoRestaurante(UUID.fromString(idRestaurante), tipoRestaurante);
            return new ResponseEntity<>("Tipo de restaurante atualizado com sucesso", HttpStatus.OK);
        } catch (ControllerNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao atualizar o tipo de restaurante", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}