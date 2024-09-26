package br.com.fiap.level3.domain.reserva.application;

import br.com.fiap.level3.domain.reserva.core.model.enums.StatusEnum;
import br.com.fiap.level3.domain.reserva.core.model.reserva.AtualizarStatusDTO;
import br.com.fiap.level3.domain.reserva.core.model.reserva.ReservaDTO;
import br.com.fiap.level3.domain.reserva.core.model.reserva.ReservaRestauranteDTO;
import br.com.fiap.level3.domain.reserva.core.ports.incoming.CreateNewReserva;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    @Qualifier("criarNovaReserva")
    private final CreateNewReserva newReserva;


    public ReservaController(CreateNewReserva newReserva) {
        this.newReserva = newReserva;
    }

    @PostMapping
    public ResponseEntity<ReservaDTO> criarReserva(@RequestBody ReservaDTO reservaDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(newReserva.createNewReserva(reservaDTO));
    }

    @GetMapping("/restaurante/{restauranteId}")
    public ResponseEntity<ReservaRestauranteDTO> listarReservasPorRestaurante(@PathVariable UUID restauranteId) {
        ReservaRestauranteDTO reservaRestauranteDTO = newReserva.listarReservasPorRestaurante(restauranteId);
        return ResponseEntity.ok(reservaRestauranteDTO);
    }

    @GetMapping("/{reservaId}")
    public ResponseEntity<ReservaDTO> listarReservaPorId(@PathVariable UUID reservaId) {
        Optional<ReservaDTO> reservaDTO = newReserva.listarReservaPorId(reservaId);
        return reservaDTO
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{reservaId}/status")
    public ResponseEntity<ReservaDTO> atualizarStatusReserva(
            @PathVariable UUID reservaId,
            @RequestBody AtualizarStatusDTO atualizarStatusDTO) {
        Optional<ReservaDTO> reservaAtualizada = newReserva.atualizarStatusReserva(reservaId, atualizarStatusDTO.novoStatus());
        return reservaAtualizada
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
