package br.com.fiap.level3.domain.tiporestaurante.core.ports;

import br.com.fiap.level3.domain.tiporestaurante.core.model.TipoRestaurante;
import br.com.fiap.level3.domain.tiporestaurante.core.model.TipoRestauranteDTO;
import br.com.fiap.level3.domain.tiporestaurante.core.ports.incoming.AddTipoRestaurante;
import br.com.fiap.level3.domain.tiporestaurante.core.ports.outcoming.TipoRestauranteDatabase;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TipoRestauranteFacade implements AddTipoRestaurante {

    private final TipoRestauranteDatabase tipoRestauranteDatabase;

    @Override
    public void handle(TipoRestauranteDTO tipoRestauranteDTO) {
        TipoRestaurante tipoRestaurante = TipoRestaurante.builder()
                .descricao(tipoRestauranteDTO.descricao())
                .build();

        this.tipoRestauranteDatabase.save(tipoRestaurante);
    }
}
