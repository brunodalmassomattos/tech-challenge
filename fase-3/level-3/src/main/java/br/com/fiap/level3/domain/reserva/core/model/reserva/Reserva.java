package br.com.fiap.level3.domain.reserva.core.model.reserva;

import br.com.fiap.level3.domain.exception.ControllerNotFoundException;
import br.com.fiap.level3.domain.reserva.core.model.enums.StatusEnum;
import br.com.fiap.level3.domain.reserva.core.model.restaurante.RestauranteReserva;
import br.com.fiap.level3.domain.reserva.core.model.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private LocalTime hora;

    @Column(name = "num_pessoas", nullable = false)
    private Integer quantidadePessoas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurante_id")
    private RestauranteReserva restaurante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(name = "status_reserva", nullable = false)
    private String status;

    public static Reserva criarReserva(ReservaDTO reservaDTO, RestauranteReserva restaurante, Usuario usuario, StatusEnum status) {
        validarCriacaoReserva(reservaDTO);
        return Reserva.builder()
                       .data(reservaDTO.data())
                       .hora(reservaDTO.hora())
                       .quantidadePessoas(reservaDTO.quantidadePessoas())
                       .restaurante(restaurante)
                       .usuario(usuario)
                       .status(status.getDescricao())
                       .build();
    }

    private static void validarCriacaoReserva(ReservaDTO reservaDTO) {
        validarCamposObrigatorios(reservaDTO.data(), reservaDTO.hora());
        validarDataHoraMaiorQueAtual(reservaDTO.data(), reservaDTO.hora());
        validarQuantidadePessoas(reservaDTO.quantidadePessoas());
    }

    private static void validarCamposObrigatorios(LocalDate data, LocalTime hora) {
        isDataNull(data);
        isHoraNull(hora);
    }

    private static void isDataNull(LocalDate data) {
        if(Objects.isNull(data)) {
            throw new ControllerNotFoundException("É necessário informar uma data para criar reserva!");
        }
    }

    private static void isHoraNull(LocalTime hora) {
        if(Objects.isNull(hora)) {
            throw new ControllerNotFoundException("É necessário informar um horário para criar reserva!");
        }
    }

    private static void validarDataHoraMaiorQueAtual(LocalDate data, LocalTime hora) {
        if (data.isBefore(LocalDate.now())) {
            throw new ControllerNotFoundException("A data informada é menor que a data atual!");
        }

        if (data.equals(LocalDate.now()) && isHoraInvalida(hora)) {
            throw new ControllerNotFoundException("Deve ser informado horário com pelo menos 30 minutos de antececência!");
        }
    }

    private static boolean isHoraInvalida(LocalTime hora) {
        long diferencaMinutos = hora.until(LocalTime.now(), ChronoUnit.MINUTES);
        return diferencaMinutos < 30;
    }

    private static void validarQuantidadePessoas(Integer quantidadePessoas) {
        if (Objects.isNull(quantidadePessoas) || quantidadePessoas < 1) {
            throw new ControllerNotFoundException("Deve haver pelo menos um cliente para criar a reserva!");
        }
    }
}
