package br.com.fiap.level3.domain.tiporestaurante.infrastructure;

import br.com.fiap.level3.domain.tiporestaurante.core.model.TipoRestaurante;
import br.com.fiap.level3.domain.tiporestaurante.core.ports.outcoming.TipoRestauranteDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TipoRestauranteDatabaseAdapterTest {
    private TipoRestauranteRepository tipoRestauranteRepository;
    private TipoRestauranteDatabase tipoRestauranteDatabase;

    @BeforeEach
    public void setUp() {
        tipoRestauranteRepository = mock(TipoRestauranteRepository.class);
        tipoRestauranteDatabase = new TipoRestauranteDatabaseAdapter(tipoRestauranteRepository);
    }

    @Test
    public void testSave() {
        TipoRestaurante tipoRestaurante = TipoRestaurante.builder().descricao("Restaurante Italiano").build();

        tipoRestauranteDatabase.save(tipoRestaurante);

        ArgumentCaptor<TipoRestaurante> captor = ArgumentCaptor.forClass(TipoRestaurante.class);
        verify(tipoRestauranteRepository, times(1)).save(captor.capture());

        TipoRestaurante savedTipoRestaurante = captor.getValue();
        assertEquals("Restaurante Italiano", savedTipoRestaurante.getDescricao());
    }

    @Test
    public void testDelete() {
        UUID id = UUID.randomUUID();

        tipoRestauranteDatabase.delete(id);

        verify(tipoRestauranteRepository, times(1)).deleteById(id);
    }

    @Test
    public void testFindById() {
        UUID id = UUID.randomUUID();
        TipoRestaurante tipoRestaurante = TipoRestaurante.builder().id(id).descricao("Restaurante Mexicano").build();

        when(tipoRestauranteRepository.findById(id)).thenReturn(Optional.of(tipoRestaurante));

        TipoRestaurante result = tipoRestauranteDatabase.findById(id);

        assertNotNull(result);
        assertEquals("Restaurante Mexicano", result.getDescricao());
    }

    @Test
    public void testFindByIdNotFound() {
        UUID id = UUID.randomUUID();

        when(tipoRestauranteRepository.findById(id)).thenReturn(Optional.empty());

        TipoRestaurante result = tipoRestauranteDatabase.findById(id);

        assertNull(result);
    }

    @Test
    public void testFindAll() {
        TipoRestaurante tipoRestaurante1 = TipoRestaurante.builder().descricao("Restaurante A").build();
        TipoRestaurante tipoRestaurante2 = TipoRestaurante.builder().descricao("Restaurante B").build();
        List<TipoRestaurante> tipoRestaurantes = new ArrayList<>();
        tipoRestaurantes.add(tipoRestaurante1);
        tipoRestaurantes.add(tipoRestaurante2);

        when(tipoRestauranteRepository.findAll()).thenReturn(tipoRestaurantes);

        List<TipoRestaurante> result = tipoRestauranteDatabase.findAll();

        assertEquals(2, result.size());
        assertEquals("Restaurante A", result.get(0).getDescricao());
        assertEquals("Restaurante B", result.get(1).getDescricao());
    }

    @Test
    public void testFindByDescricao() {
        String descricao = "Restaurante Chines";
        TipoRestaurante tipoRestaurante = TipoRestaurante.builder().descricao(descricao).build();
        List<TipoRestaurante> tipoRestaurantes = List.of(tipoRestaurante);

        when(tipoRestauranteRepository.findByDescricao(descricao)).thenReturn(tipoRestaurantes);

        List<TipoRestaurante> result = tipoRestauranteDatabase.findByDescricao(descricao);

        assertEquals(1, result.size());
        assertEquals(descricao, result.get(0).getDescricao());
    }
}