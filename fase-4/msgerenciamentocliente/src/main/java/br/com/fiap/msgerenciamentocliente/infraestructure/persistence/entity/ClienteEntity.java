package br.com.fiap.msgerenciamentocliente.infraestructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Cliente")
public class ClienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String nome;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private EnderecoEntity endereco;

    @Column(unique = true)
    private String cpf;

    @Temporal(TemporalType.DATE)
    @Column(columnDefinition = "DATE")
    private Date dataNascimento;

    private String telefone;
    private String email;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "endereco_entrega_id", referencedColumnName = "id")
    private EnderecoEntity enderecoEntrega;
}