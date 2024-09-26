package br.com.fiap.level3.domain.reserva.application;

import br.com.fiap.level3.domain.reserva.core.model.reserva.ReservaDTO;
import br.com.fiap.level3.domain.reserva.core.ports.incoming.CreateNewReserva;
import br.com.fiap.level3.domain.reserva.mocks.ReservaDTOTestMock;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReservaControllerTest {

    private MockMvc mockMvc;
    private AutoCloseable mock;

    @Mock
    private CreateNewReserva createNewReserva;

    @BeforeEach
    void setUp() {
        mock = MockitoAnnotations.openMocks(this);
        ReservaController reservaController = new ReservaController(createNewReserva);
        mockMvc = MockMvcBuilders.standaloneSetup(reservaController).build();

    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Test
    void deveCriarNovaReserva() throws Exception {
        ReservaDTO reservaCriada = ReservaDTOTestMock.getReservaCriadaComSucesso();
        ReservaDTO reservaEnviada = ReservaDTOTestMock.getReservaDTO();

        when(createNewReserva.createNewReserva(any(ReservaDTO.class))).thenReturn(reservaCriada);

        mockMvc.perform(
                post("/reservas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(reservaEnviada)))
                .andExpect(status().isCreated());
        verify(createNewReserva, times(1)).createNewReserva(any(ReservaDTO.class));
    }

    public static String asJsonString(final Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }
}