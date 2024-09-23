package br.com.fiap.level3.domain.reserva.application;

import br.com.fiap.level3.domain.reserva.core.model.reserva.ReservaDTO;
import br.com.fiap.level3.domain.reserva.core.ports.incoming.CreateNewReserva;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return ResponseEntity.ok(newReserva.createNewReserva(reservaDTO));
    }
}
