package br.com.fiap.level3.domain.tiporestaurante.application;

import br.com.fiap.level3.domain.tiporestaurante.core.model.TipoRestaurante;
import br.com.fiap.level3.domain.tiporestaurante.core.model.TipoRestauranteDTO;
import br.com.fiap.level3.domain.tiporestaurante.core.ports.outcoming.TipoRestauranteDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TipoRestauranteControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testAlteraTipoRestaurante() throws Exception {
        String id = "43ee22ea-164a-4408-a817-597f8a89fab3";

        mockMvc.perform(patch("/v1/tipo-restaurante/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"descricao\": \"árabe\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Tipo de Restaurante foi alterado!"));
    }

    @Test
    void testListaTodosTipoRestaurante() throws Exception {
        mockMvc.perform(get("/v1/tipo-restaurante"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].descricao").value("Clássico"));
    }

    @Test
    void testListaPorTipoRestaurante() throws Exception {
        mockMvc.perform(get("/v1/tipo-restaurante/descricao?descricao=Restaurante Italiano"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].descricao").value("Restaurante Italiano"));
    }

}