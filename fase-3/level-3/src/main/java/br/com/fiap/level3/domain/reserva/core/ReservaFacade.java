package br.com.fiap.level3.domain.reserva.core;

import br.com.fiap.level3.domain.exception.ControllerNotFoundException;
import br.com.fiap.level3.domain.reserva.core.model.enums.StatusEnum;
import br.com.fiap.level3.domain.reserva.core.model.reserva.Reserva;
import br.com.fiap.level3.domain.reserva.core.model.reserva.ReservaDTO;
import br.com.fiap.level3.domain.reserva.core.model.restaurante.Restaurante;
import br.com.fiap.level3.domain.reserva.core.model.usuario.Usuario;
import br.com.fiap.level3.domain.reserva.core.ports.incoming.CreateNewReserva;
import br.com.fiap.level3.domain.reserva.core.ports.outgoing.ReservaDatabase;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class ReservaFacade implements CreateNewReserva {

    private ReservaDatabase reservaDatabase;

    @Override
    public ReservaDTO createNewReserva(ReservaDTO reservaDTO) {
        Usuario usuario = getUsuarioById(reservaDTO.usuarioId());
        Restaurante restaurante = getRestauranteById(reservaDTO.restauranteId());

        Reserva reserva = Reserva.criarReserva(reservaDTO, restaurante, usuario, StatusEnum.CRIADA);

        existeReservaAberta(reserva);
        atingiuCapacidadeRestaurante(restaurante, reserva);

        Reserva reservaCriada = reservaDatabase.save(reserva);
        return ReservaDTO.convertFromReserva(reservaCriada);
    }

    private Usuario getUsuarioById(UUID usuarioId) {
        return reservaDatabase.getUsuarioById(usuarioId)
                       .orElseThrow(() -> new ControllerNotFoundException("Usuário não encontrado para o ID: " + usuarioId));
    }

    private Restaurante getRestauranteById(UUID restauranteId) {
        return reservaDatabase.getRestauranteById(restauranteId)
                       .orElseThrow(() -> new ControllerNotFoundException("Restaurante não encontrado para o ID: " + restauranteId));
    }

    private void existeReservaAberta(Reserva reserva) {
        Optional<Reserva> reservaAberta = reservaDatabase.getReservaAbertaByUsuarioAndData(
                reserva.getUsuario().getId(), reserva.getData());

        if (reservaAberta.isPresent()) {
            throw new ControllerNotFoundException(
                    String.format("Existe uma reserva aberta no mesmo dia para o usuário: %s", reserva.getUsuario().getNome()));
        }
    }

    private void atingiuCapacidadeRestaurante(Restaurante restaurante, Reserva novaReserva) {

        Long quantidadeLugaresOcupados = reservaDatabase.getQuantidadeLugaresReservadosByRestaurante(restaurante.getId());
        int totalLugaresOcupados = Integer.sum(novaReserva.getQuantidadePessoas(), quantidadeLugaresOcupados.intValue());

        if (totalLugaresOcupados > restaurante.getCapacidade()) {
            throw new ControllerNotFoundException(
                    String.format("Atingiu a capacidade do restaurante, restam apenas %d lugares",
                            (restaurante.getCapacidade() - quantidadeLugaresOcupados)));
        }
    }
}
