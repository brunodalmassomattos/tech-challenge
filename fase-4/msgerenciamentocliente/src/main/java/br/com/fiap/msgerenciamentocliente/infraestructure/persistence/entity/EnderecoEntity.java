package br.com.fiap.msgerenciamentocliente.infraestructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Endereco")
public class EnderecoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String rua;
    private String bairro;
    private String complemento = "";
    private int numero;
    private String cidade;
    private String estado;
    private String cep;

    @OneToOne(mappedBy = "endereco")
    private ClienteEntity cliente;

    @OneToOne(mappedBy = "enderecoEntrega")
    private ClienteEntity clienteEntrega;
}