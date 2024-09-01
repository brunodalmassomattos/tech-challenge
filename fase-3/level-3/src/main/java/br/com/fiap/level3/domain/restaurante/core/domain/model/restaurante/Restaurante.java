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
}
