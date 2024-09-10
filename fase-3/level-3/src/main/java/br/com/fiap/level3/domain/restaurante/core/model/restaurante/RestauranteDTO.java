package br.com.fiap.level3.domain.restaurante.core.model.restaurante;

import br.com.fiap.level3.domain.restaurante.core.model.endereco.Endereco;
import br.com.fiap.level3.domain.restaurante.core.model.endereco.EnderecoDTO;
import br.com.fiap.level3.domain.restaurante.core.model.tiporestaurante.TipoRestaurante;
import br.com.fiap.level3.domain.restaurante.core.model.tiporestaurante.TipoRestauranteDTO;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record RestauranteDTO(String id,
                             String nome,
                             String horarioFuncionamento,
                             int capacidade,
                             boolean status,
                             TipoRestauranteDTO tipoRestaurante,
                             EnderecoDTO endereco) {

    public static RestauranteDTO fromRestaurante(Restaurante restaurante) {
        return new RestauranteDTO(
                restaurante.getId().toString(),
                restaurante.getNome(),
                restaurante.getHorarioFuncionamento(),
                restaurante.getCapacidade(),
                restaurante.isStatus(),
                TipoRestauranteDTO.fromTipoRestaurante(restaurante.getTipoRestaurante()),
                EnderecoDTO.fromEndereco(restaurante.getEndereco()));
    }

    public static List<RestauranteDTO> fromRestaurantes(List<Restaurante> restaurantes) {
       return restaurantes
               .stream()
               .map(RestauranteDTO::fromRestaurante)
               .collect(Collectors.toList());
    }

    public static Restaurante toRestaurante(String id, RestauranteDTO restaurante) {
        return new Restaurante(
                (id != null) ? UUID.fromString(id) : null,
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
