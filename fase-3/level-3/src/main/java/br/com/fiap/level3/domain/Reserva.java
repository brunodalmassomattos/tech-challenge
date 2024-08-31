package br.com.fiap.level3.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "reservas")
public class Reserva {
    @Id
    @GeneratedValue
    private UUID id;

    private LocalDate data;
    private LocalTime hora;
    private int numeroPessoas;

    @ManyToOne
    @JoinColumn(name = "id_restaurante")
    private Restaurante restaurante;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    private StatusReserva status;

    public enum StatusReserva {
        CONFIRMADA,
        CANCELADA,
        AGUARDANDO_CONFIRMACAO
    }
}