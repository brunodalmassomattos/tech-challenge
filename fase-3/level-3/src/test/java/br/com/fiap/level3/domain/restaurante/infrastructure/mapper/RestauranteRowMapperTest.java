package br.com.fiap.level3.domain.restaurante.infrastructure.mapper;

import br.com.fiap.level3.domain.restaurante.core.model.restaurante.Restaurante;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RestauranteRowMapperTest {

    @Test
    public void testMapRow() throws SQLException {
        // Arrange
        ResultSet rs = Mockito.mock(ResultSet.class);
        UUID tipoRestauranteId = UUID.randomUUID();
        UUID enderecoId = UUID.randomUUID();

        when(rs.getString("id")).thenReturn(UUID.randomUUID().toString());
        when(rs.getString("nome")).thenReturn("Restaurante Teste");
        when(rs.getString("horario_funcionamento")).thenReturn("10:00 - 22:00");
        when(rs.getInt("capacidade")).thenReturn(100);
        when(rs.getBoolean("status")).thenReturn(true);
        when(rs.getString("tipo_restaurante_id")).thenReturn(tipoRestauranteId.toString());
        when(rs.getString("descricao")).thenReturn("Tipo Teste");
        when(rs.getString("endereco_id")).thenReturn(enderecoId.toString());
        when(rs.getString("rua")).thenReturn("Rua Teste");
        when(rs.getString("numero")).thenReturn("123");
        when(rs.getString("bairro")).thenReturn("Bairro Teste");
        when(rs.getString("cidade")).thenReturn("Cidade Teste");
        when(rs.getString("estado")).thenReturn("Estado Teste");
        when(rs.getString("cep")).thenReturn("12345-678");

        RestauranteRowMapper mapper = new RestauranteRowMapper();

        // Act
        Restaurante restaurante = mapper.mapRow(rs, 1);

        // Assert
        assertEquals("Restaurante Teste", restaurante.getNome());
        assertEquals("10:00 - 22:00", restaurante.getHorarioFuncionamento());
        assertEquals(100, restaurante.getCapacidade());
        assertEquals(true, restaurante.isStatus());
        assertEquals(tipoRestauranteId, restaurante.getTipoRestaurante().getId());
        assertEquals("Tipo Teste", restaurante.getTipoRestaurante().getDescricao());
        assertEquals(enderecoId, restaurante.getEndereco().getId());
        assertEquals("Rua Teste", restaurante.getEndereco().getLogradouro());
        assertEquals("123", restaurante.getEndereco().getNumero());
        assertEquals("Bairro Teste", restaurante.getEndereco().getBairro());
        assertEquals("Cidade Teste", restaurante.getEndereco().getCidade());
        assertEquals("Estado Teste", restaurante.getEndereco().getEstado());
        assertEquals("12345-678", restaurante.getEndereco().getCep());
    }
}