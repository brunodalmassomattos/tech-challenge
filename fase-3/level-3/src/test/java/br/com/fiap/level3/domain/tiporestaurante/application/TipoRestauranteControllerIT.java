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

    @Autowired
    private TipoRestauranteDatabase tipoRestauranteDatabase; // Mockar o repositório, se necessário

    private TipoRestaurante tipoRestaurante;

    @BeforeEach
    public void setUp() {
        tipoRestaurante = TipoRestaurante.builder()
                .id(UUID.randomUUID())
                .descricao("Restaurante Italiano")
                .build();

        // Se o repositório não é mockado, você pode precisar usar um banco de dados em memória (ex: H2)
        // Adicione o tipo de restaurante ao banco de dados, se necessário
        tipoRestauranteDatabase.save(tipoRestaurante);
    }

    @Test
    public void testAdicionaTipoRestaurante() throws Exception {
        TipoRestauranteDTO dto = new TipoRestauranteDTO(null, "Restaurante Chines");

        mockMvc.perform(post("/v1/tipo-restaurante")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"descricao\": \"Restaurante Chines\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Novo Tipo de Restaurante foi incluido!"));
    }
    @Test
    public void testAlteraTipoRestaurante() throws Exception {
        String id = tipoRestaurante.getId().toString();
        TipoRestauranteDTO dto = new TipoRestauranteDTO(null, "Restaurante Frances");

        mockMvc.perform(patch("/v1/tipo-restaurante/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"descricao\": \"Restaurante Frances\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Tipo de Restaurante foi alterado!"));
    }

    @Test
    public void testDeletaTipoRestaurante() throws Exception {
        String id = tipoRestaurante.getId().toString();

        mockMvc.perform(delete("/v1/tipo-restaurante/" + id))
                .andExpect(status().isNoContent())
                .andExpect(content().string("Tipo de Restaurante foi alterado!"));
    }

    @Test
    public void testListaTodosTipoRestaurante() throws Exception {
        mockMvc.perform(get("/v1/tipo-restaurante"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].descricao").value("Restaurante Italiano"));
    }

    @Test
    public void testListaPorTipoRestaurante() throws Exception {
        mockMvc.perform(get("/v1/tipo-restaurante/descricao?descricao=Restaurante Italiano"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].descricao").value("Restaurante Italiano"));
    }

}