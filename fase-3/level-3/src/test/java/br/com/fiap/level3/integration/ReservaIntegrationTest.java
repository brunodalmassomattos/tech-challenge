package br.com.fiap.level3.integration;

import br.com.fiap.level3.domain.reserva.core.model.enums.StatusEnum;
import br.com.fiap.level3.domain.reserva.core.model.reserva.ReservaDTO;
import br.com.fiap.level3.domain.reserva.core.model.reserva.ReservaRestauranteDTO;
import br.com.fiap.level3.domain.reserva.core.ports.incoming.CreateNewReserva;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ReservaIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateNewReserva createNewReserva;

    @Test
    public void testListarReservasPorRestaurante() throws Exception {
        UUID restauranteId = UUID.randomUUID();
        ReservaDTO reserva1 = new ReservaDTO(UUID.randomUUID(), LocalDate.now(), LocalTime.now(), 2, restauranteId, UUID.randomUUID(), StatusEnum.CRIADA.getDescricao());
        ReservaDTO reserva2 = new ReservaDTO(UUID.randomUUID(), LocalDate.now(), LocalTime.now(), 3, restauranteId, UUID.randomUUID(), StatusEnum.CRIADA.getDescricao());
        ReservaRestauranteDTO reservaRestauranteDTO = new ReservaRestauranteDTO(Arrays.asList(reserva1, reserva2), 5, 10, true);

        when(createNewReserva.listarReservasPorRestaurante(restauranteId)).thenReturn(reservaRestauranteDTO);

        mockMvc.perform(get("/reservas/restaurante/" + restauranteId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reservas.length()").value(2))
                .andExpect(jsonPath("$.totalPessoas").value(5))
                .andExpect(jsonPath("$.capacidadeRestaurante").value(10))
                .andExpect(jsonPath("$.podeAceitarMaisReservas").value(true))
                .andDo(print());
    }

    @Test
    public void testListarReservaPorId() throws Exception {
        UUID reservaId = UUID.randomUUID();
        ReservaDTO reservaDTO = new ReservaDTO(reservaId, LocalDate.now(), LocalTime.now(), 2, UUID.randomUUID(), UUID.randomUUID(), StatusEnum.CRIADA.getDescricao());

        when(createNewReserva.listarReservaPorId(reservaId)).thenReturn(Optional.of(reservaDTO));

        mockMvc.perform(get("/reservas/" + reservaId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(reservaId.toString()))
                .andExpect(jsonPath("$.status").value(StatusEnum.CRIADA.getDescricao()))
                .andDo(print());
    }

    @Test
    public void testAtualizarStatusReserva() throws Exception {
        UUID reservaId = UUID.randomUUID();
        StatusEnum novoStatus = StatusEnum.CONFIRMADA;
        ReservaDTO reservaAtualizada = new ReservaDTO(reservaId, LocalDate.now(), LocalTime.now(), 2, UUID.randomUUID(), UUID.randomUUID(), novoStatus.getDescricao());

        when(createNewReserva.atualizarStatusReserva(reservaId, novoStatus)).thenReturn(Optional.of(reservaAtualizada));

        mockMvc.perform(patch("/reservas/" + reservaId + "/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"novoStatus\": \"" + novoStatus + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(reservaId.toString()))
                .andExpect(jsonPath("$.status").value(novoStatus.getDescricao()))
                .andDo(print());
    }
}
