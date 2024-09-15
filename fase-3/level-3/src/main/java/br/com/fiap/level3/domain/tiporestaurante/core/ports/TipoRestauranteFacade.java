package br.com.fiap.level3.domain.tiporestaurante.core.ports;

import br.com.fiap.level3.domain.tiporestaurante.core.model.TipoRestaurante;
import br.com.fiap.level3.domain.tiporestaurante.core.model.TipoRestauranteDTO;
import br.com.fiap.level3.domain.tiporestaurante.core.ports.incoming.AddTipoRestaurante;
import br.com.fiap.level3.domain.tiporestaurante.core.ports.incoming.AlterTipoRestaurante;
import br.com.fiap.level3.domain.tiporestaurante.core.ports.incoming.DeleteTipoRestaurante;
import br.com.fiap.level3.domain.tiporestaurante.core.ports.incoming.FindTipoRestaurante;
import br.com.fiap.level3.domain.tiporestaurante.core.ports.outcoming.TipoRestauranteDatabase;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class TipoRestauranteFacade implements AddTipoRestaurante, AlterTipoRestaurante, DeleteTipoRestaurante, FindTipoRestaurante {

    private final TipoRestauranteDatabase tipoRestauranteDatabase;

    @Override
    public void handle(TipoRestauranteDTO tipoRestauranteDTO) {
        TipoRestaurante tipoRestaurante = TipoRestaurante.builder()
                .descricao(tipoRestauranteDTO.descricao())
                .build();

        this.tipoRestauranteDatabase.save(tipoRestaurante);
    }

    @Override
    public void alterData(String id, TipoRestauranteDTO tipoRestauranteDTO) {
        TipoRestaurante tipoRestaurante = TipoRestaurante.builder()
                .id(UUID.fromString(id))
                .descricao(tipoRestauranteDTO.descricao())
                .build();
        this.tipoRestauranteDatabase.save(tipoRestaurante);
    }

    @Override
    public void handle(String id) {
        this.tipoRestauranteDatabase.delete(UUID.fromString(id));
    }

    @Override
    public Optional<TipoRestaurante> getTipoRestauranteById(UUID id) {
        return Optional.ofNullable(this.tipoRestauranteDatabase.findById(id));
    }

    @Override
    public List<TipoRestaurante> getTipoRestauranteByDescricao(String descricao) {
        return this.tipoRestauranteDatabase.findByDescricao(descricao);
    }

    @Override
    public List<TipoRestaurante> getTipoRestaurantes() {
        return this.tipoRestauranteDatabase.findAll();
    }
}
