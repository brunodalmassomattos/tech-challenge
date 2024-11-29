package br.com.fiap.funcionalidadeDeCargaDeProdutos.infrastructure.api;

import br.com.fiap.funcionalidadeDeCargaDeProdutos.application.dto.CategoriaDTO;
import br.com.fiap.funcionalidadeDeCargaDeProdutos.application.service.CategoriaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CategoriaControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CategoriaService categoriaService;

    @InjectMocks
    private CategoriaController categoriaController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void deveCriarCategoria() throws Exception {
        UUID categoriaId = UUID.randomUUID();
        CategoriaDTO categoriaDTO = new CategoriaDTO(null, "Eletrônicos");
        CategoriaDTO categoriaCriada = new CategoriaDTO(categoriaId, "Eletrônicos");

        when(categoriaService.createCategoria(any())).thenReturn(categoriaCriada);

        mockMvc = MockMvcBuilders.standaloneSetup(categoriaController).build();

        mockMvc.perform(post("/api/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoriaDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(categoriaId.toString())))
                .andExpect(jsonPath("$.descricao", is("Eletrônicos")));

        verify(categoriaService, times(1)).createCategoria(any());
    }

    @Test
    void deveListarCategorias() throws Exception {
        UUID categoriaId = UUID.randomUUID();
        CategoriaDTO categoriaDTO = new CategoriaDTO(categoriaId, "Eletrônicos");
        List<CategoriaDTO> categorias = Arrays.asList(categoriaDTO);

        when(categoriaService.getAllCategorias()).thenReturn(categorias);

        mockMvc = MockMvcBuilders.standaloneSetup(categoriaController).build();

        mockMvc.perform(get("/api/categorias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(categoriaId.toString())))
                .andExpect(jsonPath("$[0].descricao", is("Eletrônicos")));

        verify(categoriaService, times(1)).getAllCategorias();
    }

    @Test
    void deveRetornarCategoriaPorId() throws Exception {
        UUID categoriaId = UUID.randomUUID();
        CategoriaDTO categoriaDTO = new CategoriaDTO(categoriaId, "Eletrônicos");

        when(categoriaService.getCategoriaById(categoriaId)).thenReturn(categoriaDTO);

        mockMvc = MockMvcBuilders.standaloneSetup(categoriaController).build();

        mockMvc.perform(get("/api/categorias/{id}", categoriaId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(categoriaId.toString())))
                .andExpect(jsonPath("$.descricao", is("Eletrônicos")));

        verify(categoriaService, times(1)).getCategoriaById(categoriaId);
    }

    @Test
    void deveAtualizarCategoria() throws Exception {
        UUID categoriaId = UUID.randomUUID();
        CategoriaDTO categoriaDTO = new CategoriaDTO(null, "Informática");
        CategoriaDTO categoriaAtualizada = new CategoriaDTO(categoriaId, "Informática");

        when(categoriaService.updateCategoria(eq(categoriaId), any())).thenReturn(categoriaAtualizada);

        mockMvc = MockMvcBuilders.standaloneSetup(categoriaController).build();

        mockMvc.perform(put("/api/categorias/{id}", categoriaId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoriaDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(categoriaId.toString())))
                .andExpect(jsonPath("$.descricao", is("Informática")));

        verify(categoriaService, times(1)).updateCategoria(eq(categoriaId), any());
    }

    @Test
    void deveExcluirCategoria() throws Exception {
        UUID categoriaId = UUID.randomUUID();

        mockMvc = MockMvcBuilders.standaloneSetup(categoriaController).build();

        mockMvc.perform(delete("/api/categorias/{id}", categoriaId))
                .andExpect(status().isNoContent());

        verify(categoriaService, times(1)).deleteCategoria(categoriaId);
    }
}

