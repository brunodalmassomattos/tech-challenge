package br.com.fiap.level3.domain;

import br.com.fiap.level3.domain.reserva.core.model.usuario.Usuario;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "avaliacoes")
public class Avaliacao {
    @Id
    @GeneratedValue
    private UUID id;

    private int nota;

    private String comentario;

    @ManyToOne
    @JoinColumn(name = "id_restaurante")
    private Restaurante restaurante;

    @ManyToOne
    @JoinColumn(name= "id_usuario")
    private Usuario usuario;
}
