package br.com.fiap.msgerenciamentocliente.domain.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Endereco {
    private UUID id;
    private String rua;
    private String bairro;
    private String complemento = "";
    private int numero;
    private String cidade;
    private String estado;
    private String cep;
}