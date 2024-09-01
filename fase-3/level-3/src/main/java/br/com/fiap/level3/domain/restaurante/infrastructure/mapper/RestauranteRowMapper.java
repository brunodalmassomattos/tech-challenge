package br.com.fiap.level3.domain.restaurante.infrastructure.mapper;

import br.com.fiap.level3.domain.restaurante.core.domain.model.endereco.Endereco;
import br.com.fiap.level3.domain.restaurante.core.domain.model.restaurante.Restaurante;

import br.com.fiap.level3.domain.restaurante.core.domain.model.tiporestaurante.TipoRestaurante;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class RestauranteRowMapper implements RowMapper<Restaurante> {

    @Override
    public Restaurante mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Restaurante.builder()
                .id(UUID.fromString(rs.getString("id")))
                .nome(rs.getString("nome"))
                .horarioFuncionamento(rs.getString("horario_funcionamento"))
                .capacidade(rs.getInt("capacidade"))
                .status(rs.getBoolean("status"))
                .tipoRestaurante(
                        TipoRestaurante.builder()
                                .id(UUID.fromString(rs.getString("tipo_restaurante_id")))
                                .descricao(rs.getString("descricao"))
                                .build()
                )
                .endereco(
                        Endereco.builder()
                                .id(UUID.fromString(rs.getString("id")))
                                .logradouro(rs.getString("rua"))
                                .numero(rs.getString("numero"))
                                .bairro(rs.getString("bairro"))
                                .cidade(rs.getString("cidade"))
                                .estado(rs.getString("estado"))
                                .cep(rs.getString("cep"))
                                .build())
                .build();
    }
}
