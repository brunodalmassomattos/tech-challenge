package br.com.fiap.level3.domain.restaurante.core.model.endereco;

import jakarta.persistence.Column;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {
    private UUID id;
    private String cep;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;

    public Endereco(String cep, String logradouro, String numero, String bairro, String cidade, String estado) {
        this.cep = cep;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
    }
}
