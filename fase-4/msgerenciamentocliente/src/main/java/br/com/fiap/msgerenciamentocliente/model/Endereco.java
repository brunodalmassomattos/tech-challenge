package br.com.fiap.msgerenciamentocliente.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Endereco")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String rua;
    private String bairro;
    private String complemento;
    private int numero;
    private String cidade;
    private String estado;
    private String cep;
}
