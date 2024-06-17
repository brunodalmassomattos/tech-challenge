package br.com.fiap.wework.domain.usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String nome;

    @Column(name = "CPF_CNPJ")
    private String cpfCnpj;

    @Temporal(TemporalType.DATE)
    private Date dataNascimento;

    @Temporal(TemporalType.DATE)
    private Date dataAbertura;

    private String email;

    private String senha;

    @ManyToOne
    private Perfil perfil;

    private String status;
}
