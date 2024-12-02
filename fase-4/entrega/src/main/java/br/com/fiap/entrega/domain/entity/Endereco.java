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
@Entity(name = "entrega.Endereco")
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

    public static Endereco toEntity(EnderecoEntregaDto enderecoDto) {
        return Endereco.builder()
                       .id(enderecoDto.id())
                       .estado(enderecoDto.estado())
                       .cidade(enderecoDto.cidade())
                       .bairro(enderecoDto.bairro())
                       .rua(enderecoDto.rua())
                       .numero(enderecoDto.numero())
                       .cep(enderecoDto.cep())
                       .build();
    }
}
