package br.com.fiap.level3.domain.tiporestaurante.core.ports;

import br.com.fiap.level3.domain.tiporestaurante.core.model.TipoRestaurante;
import br.com.fiap.level3.domain.tiporestaurante.core.model.TipoRestauranteDTO;
import br.com.fiap.level3.domain.tiporestaurante.core.ports.outcoming.TipoRestauranteDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TipoRestauranteFacadeTest {

    private TipoRestauranteDatabase tipoRestauranteDatabase;
    private TipoRestauranteFacade tipoRestauranteFacade;

    @BeforeEach
    public void setUp() {
        tipoRestauranteDatabase = mock(TipoRestauranteDatabase.class);
        tipoRestauranteFacade = new TipoRestauranteFacade(tipoRestauranteDatabase);
    }

    @Test
    public void testHandle() {
        TipoRestauranteDTO dto = new TipoRestauranteDTO(null, "Restaurante Italiano");

        tipoRestauranteFacade.handle(dto);

        ArgumentCaptor<TipoRestaurante> captor = ArgumentCaptor.forClass(TipoRestaurante.class);
        verify(tipoRestauranteDatabase, times(1)).save(captor.capture());

        TipoRestaurante tipoRestaurante = captor.getValue();
        assertNull(tipoRestaurante.getId()); // ID deve ser gerado no banco
        assertEquals("Restaurante Italiano", tipoRestaurante.getDescricao());
    }

    @Test
    public void testAlterData() {
        String id = UUID.randomUUID().toString();
        TipoRestauranteDTO dto = new TipoRestauranteDTO(null, "Restaurante Francês");

        tipoRestauranteFacade.alterData(id, dto);

        ArgumentCaptor<TipoRestaurante> captor = ArgumentCaptor.forClass(TipoRestaurante.class);
        verify(tipoRestauranteDatabase, times(1)).save(captor.capture());

        TipoRestaurante tipoRestaurante = captor.getValue();
        assertEquals(UUID.fromString(id), tipoRestaurante.getId());
        assertEquals("Restaurante Francês", tipoRestaurante.getDescricao());
    }

    @Test
    public void testHandleDelete() {
        String id = UUID.randomUUID().toString();

        tipoRestauranteFacade.handle(id);

        verify(tipoRestauranteDatabase, times(1)).delete(UUID.fromString(id));
    }

    @Test
    public void testGetTipoRestauranteById() {
        UUID id = UUID.randomUUID();
        TipoRestaurante tipoRestaurante = TipoRestaurante.builder().id(id).descricao("Restaurante Mexicano").build();

        when(tipoRestauranteDatabase.findById(id)).thenReturn(tipoRestaurante);

        Optional<TipoRestaurante> result = tipoRestauranteFacade.getTipoRestauranteById(id);

        assertTrue(result.isPresent());
        assertEquals(tipoRestaurante, result.get());
    }

    @Test
    public void testGetTipoRestauranteByDescricao() {
        String descricao = "Restaurante Chines";
        TipoRestaurante tipoRestaurante = TipoRestaurante.builder().descricao(descricao).build();

        when(tipoRestauranteDatabase.findByDescricao(descricao)).thenReturn(List.of(tipoRestaurante));

        List<TipoRestaurante> result = tipoRestauranteFacade.getTipoRestauranteByDescricao(descricao);

        assertEquals(1, result.size());
        assertEquals(descricao, result.get(0).getDescricao());
    }

    @Test
    public void testGetTipoRestaurantes() {
        TipoRestaurante tipoRestaurante1 = TipoRestaurante.builder().descricao("Restaurante A").build();
        TipoRestaurante tipoRestaurante2 = TipoRestaurante.builder().descricao("Restaurante B").build();

        when(tipoRestauranteDatabase.findAll()).thenReturn(List.of(tipoRestaurante1, tipoRestaurante2));

        List<TipoRestaurante> result = tipoRestauranteFacade.getTipoRestaurantes();

        assertEquals(2, result.size());
        assertEquals("Restaurante A", result.get(0).getDescricao());
        assertEquals("Restaurante B", result.get(1).getDescricao());
    }

}