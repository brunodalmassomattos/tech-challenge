package br.com.fiap.level3.domain.restaurante.core.domain.model.restaurante;

import br.com.fiap.level3.domain.restaurante.core.domain.model.endereco.Endereco;
import br.com.fiap.level3.domain.restaurante.core.domain.model.tiporestaurante.TipoRestaurante;
import lombok.*;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Restaurante {
    private UUID id;
    private String nome;
    private String horarioFuncionamento;
    private int capacidade;
    private boolean status;
    private TipoRestaurante tipoRestaurante;
    private Endereco endereco;

    public Restaurante(String nome,
                       String horarioFuncionamento,
                       int capacidade,
                       boolean status,
                       TipoRestaurante tipoRestaurante,
                       Endereco endereco) {
        this.nome = nome;
        this.horarioFuncionamento = horarioFuncionamento;
        this.capacidade = capacidade;
        this.status = status;
        this.tipoRestaurante = tipoRestaurante;
        this.endereco = endereco;
    }
}
