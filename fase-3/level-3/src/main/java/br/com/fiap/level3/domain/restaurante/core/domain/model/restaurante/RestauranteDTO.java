package br.com.fiap.level3.domain.restaurante.core.domain.model.restaurante;

import br.com.fiap.level3.domain.restaurante.core.domain.model.endereco.Endereco;
import br.com.fiap.level3.domain.restaurante.core.domain.model.endereco.EnderecoDTO;
import br.com.fiap.level3.domain.restaurante.core.domain.model.tiporestaurante.TipoRestaurante;
import br.com.fiap.level3.domain.restaurante.core.domain.model.tiporestaurante.TipoRestauranteDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public record RestauranteDTO(String id,
                             String nome,
                             String horarioFuncionamento,
                             int capacidade,
                             boolean status,
                             TipoRestauranteDTO tipoRestaurante,
                             EnderecoDTO endereco) {

    public static RestauranteDTO fromRestaurante(Optional<Restaurante> restaurante) {
        return new RestauranteDTO(
                restaurante.get().getId().toString(),
                restaurante.get().getNome(),
                restaurante.get().getHorarioFuncionamento(),
                restaurante.get().getCapacidade(),
                restaurante.get().isStatus(),
                TipoRestauranteDTO.fromTipoRestaurante(restaurante.get().getTipoRestaurante()),
                EnderecoDTO.fromEndereco(restaurante.get().getEndereco()));
    }

    public static List<RestauranteDTO> fromRestaurantes(List<Restaurante> restaurantes) {
       return restaurantes
               .stream()
               .map(restaurante -> RestauranteDTO.fromRestaurante(Optional.ofNullable(restaurante)))
               .collect(Collectors.toList())
               ;
    }

    public static Restaurante toRestaurante(RestauranteDTO restaurante) {
        return new Restaurante(
                restaurante.nome,
                restaurante.horarioFuncionamento,
                restaurante.capacidade,
                restaurante.status(),
                new TipoRestaurante(UUID.fromString(restaurante.tipoRestaurante.id())),
                new Endereco(
                        restaurante.endereco.cep(),
                        restaurante.endereco.logradouro(),
                        restaurante.endereco.numero(),
                        restaurante.endereco.bairro(),
                        restaurante.endereco.cidade(),
                        restaurante.endereco.uf())
        );
    }
}
