package br.com.fiap.entrega.domain.entity;

import br.com.fiap.entrega.application.dto.EnderecoEntregaDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Endereco")
public class Endereco {

    @Id
    private UUID id;

    private String estado;

    private String cidade;

    private String bairro;

    private String rua;

    private String numero;

    private String cep;

    public static EnderecoEntregaDto toDto(Endereco endereco) {
        return EnderecoEntregaDto.builder()
                       .id(endereco.getId())
                       .estado(endereco.getEstado())
                       .cidade(endereco.getCidade())
                       .bairro(endereco.getBairro())
                       .rua(endereco.getRua())
                       .numero(endereco.getNumero())
                       .cep(endereco.getCep())
                       .build();
    }
}
