package br.com.fiap.level3.domain.avaliacao.application;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import br.com.fiap.level3.domain.avaliacao.core.domain.model.avaliacao.Avaliacao;
import br.com.fiap.level3.domain.avaliacao.mocks.AvaliacaoTestMock;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AvaliacaoControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAdicionaAvaliacao() throws Exception {
        mockMvc.perform(post("/avaliacao")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nota\": 10,\"comentario\":\"Legalaaaa\",\"restauranteId\":\"7ceb223e-0bdf-47a5-bd2d-1cff74715b1e\",\"usuarioId\":\"11033158-f69c-4c4e-a577-70721310983e\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Nova Avaliacao cadastrada"));
    }
    
    @Test
    public void testAlterarAvaliacaoNota() throws Exception {
        
    	 mockMvc.perform(patch("/avaliacao/" + "61f291a8-4c53-4718-b01c-8369de3e11b5")
    			 .contentType(MediaType.APPLICATION_JSON)
    			 .content("{\"nota\":2}")).andExpect(status().isOk()).andExpect(content().string("Avaliacao Alterada"));
    }
    
    @Test
    public void testAlterarAvaliacaoComentario() throws Exception {
        
    	 mockMvc.perform(patch("/avaliacao/" + "61f291a8-4c53-4718-b01c-8369de3e11b5")
    			 .contentType(MediaType.APPLICATION_JSON)
    			 .content("{\"comentario\":\"Chatooo\"}")).andExpect(status().isOk()).andExpect(content().string("Avaliacao Alterada"));
    }
    
    @Test
    public void testAlterarAvaliacaoComentarioENota() throws Exception {
        
    	 mockMvc.perform(patch("/avaliacao/" + "61f291a8-4c53-4718-b01c-8369de3e11b5")
    			 .contentType(MediaType.APPLICATION_JSON)
    			 .content("{\"comentario\":\"Ate que vai\",\"nota\":8}")).andExpect(status().isOk()).andExpect(content().string("Avaliacao Alterada"));
    }
    
    @Test
    void buscaAvaliacaoById() throws Exception {
    	
    	mockMvc.perform(get("/avaliacao/" + "61f291a8-4c53-4718-b01c-8369de3e11b5"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.nota").value(2));    	
    }
    
    @Test
    void buscaAvaliacaoRestauranteId() throws Exception {
    	
        String restauranteId = "7ceb223e-0bdf-47a5-bd2d-1cff74715b1e";

        mockMvc.perform(get("/avaliacao/restauranteId")
                .param("restauranteId", restauranteId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].restauranteId").value("7ceb223e-0bdf-47a5-bd2d-1cff74715b1e"));  	
    }
    
    @Test
    void buscaAvaliacaoUsuarioId() throws Exception {
    	
        String usuarioId = "11033158-f69c-4c4e-a577-70721310983e";

        mockMvc.perform(get("/avaliacao/usuarioId")
                .param("usuarioId", usuarioId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].usuarioId").value("11033158-f69c-4c4e-a577-70721310983e"));  	
    }
    
    

}
