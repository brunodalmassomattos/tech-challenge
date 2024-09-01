package br.com.fiap.level3.domain.restaurante.core.domain.model.restaurante;

import br.com.fiap.level3.domain.restaurante.core.domain.model.endereco.EnderecoDTO;
import br.com.fiap.level3.domain.restaurante.core.domain.model.tiporestaurante.TipoRestauranteDTO;

import java.util.Optional;

public record RestauranteDTO(String id,
                             String nome,
                             TipoRestauranteDTO tipoRestaurante,
                             String horarioFuncionamento,
                             int capacidade,
                             EnderecoDTO endereco,
                             boolean status) {

    public static RestauranteDTO fromRestaurante(Optional<Restaurante> restaurante) {
        return new RestauranteDTO(
                restaurante.get().getId().toString(),
                restaurante.get().getNome(),
                TipoRestauranteDTO.fromTipoRestaurante(restaurante.get().getTipoRestaurante()),
                restaurante.get().getHorarioFuncionamento(),
                restaurante.get().getCapacidade(),
                EnderecoDTO.fromEndereco(restaurante.get().getEndereco()),
                restaurante.get().isStatus());
    }
}
