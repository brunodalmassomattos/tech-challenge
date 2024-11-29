package br.com.fiap.funcionalidadeDeCargaDeProdutos.infrastructure.api;

import br.com.fiap.funcionalidadeDeCargaDeProdutos.application.dto.ProdutoDTO;
import br.com.fiap.funcionalidadeDeCargaDeProdutos.application.service.ProdutoService;
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
class ProdutoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProdutoService produtoService;

    @InjectMocks
    private ProdutoController produtoController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void deveCriarProduto() throws Exception {
        UUID produtoId = UUID.randomUUID();
        UUID categoriaId = UUID.randomUUID();
        ProdutoDTO produtoDTO = new ProdutoDTO(null, "Smartphone XYZ", "Descrição", 2999.99, 50, categoriaId);
        ProdutoDTO produtoCriado = new ProdutoDTO(produtoId, "Smartphone XYZ", "Descrição", 2999.99, 50, categoriaId);

        when(produtoService.createProduto(any())).thenReturn(produtoCriado);

        mockMvc = MockMvcBuilders.standaloneSetup(produtoController).build();

        mockMvc.perform(post("/api/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produtoDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(produtoId.toString())))
                .andExpect(jsonPath("$.nome", is("Smartphone XYZ")));

        verify(produtoService, times(1)).createProduto(any());
    }

    @Test
    void deveListarProdutos() throws Exception {
        UUID produtoId = UUID.randomUUID();
        UUID categoriaId = UUID.randomUUID();
        ProdutoDTO produtoDTO = new ProdutoDTO(produtoId, "Smartphone XYZ", "Descrição", 2999.99, 50, categoriaId);
        List<ProdutoDTO> produtos = Arrays.asList(produtoDTO);

        when(produtoService.getAllProdutos()).thenReturn(produtos);

        mockMvc = MockMvcBuilders.standaloneSetup(produtoController).build();

        mockMvc.perform(get("/api/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(produtoId.toString())))
                .andExpect(jsonPath("$[0].nome", is("Smartphone XYZ")));

        verify(produtoService, times(1)).getAllProdutos();
    }

    @Test
    void deveRetornarProdutoPorId() throws Exception {
        UUID produtoId = UUID.randomUUID();
        UUID categoriaId = UUID.randomUUID();
        ProdutoDTO produtoDTO = new ProdutoDTO(produtoId, "Smartphone XYZ", "Descrição", 2999.99, 50, categoriaId);

        when(produtoService.getProdutoById(produtoId)).thenReturn(produtoDTO);

        mockMvc = MockMvcBuilders.standaloneSetup(produtoController).build();

        mockMvc.perform(get("/api/produtos/{id}", produtoId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(produtoId.toString())))
                .andExpect(jsonPath("$.nome", is("Smartphone XYZ")));

        verify(produtoService, times(1)).getProdutoById(produtoId);
    }

    @Test
    void deveAtualizarProduto() throws Exception {
        UUID produtoId = UUID.randomUUID();
        UUID categoriaId = UUID.randomUUID();
        ProdutoDTO produtoDTO = new ProdutoDTO(null, "Smartphone XYZ Pro", "Descrição atualizada", 3299.99, 40, categoriaId);
        ProdutoDTO produtoAtualizado = new ProdutoDTO(produtoId, "Smartphone XYZ Pro", "Descrição atualizada", 3299.99, 40, categoriaId);

        when(produtoService.updateProduto(eq(produtoId), any())).thenReturn(produtoAtualizado);

        mockMvc = MockMvcBuilders.standaloneSetup(produtoController).build();

        mockMvc.perform(put("/api/produtos/{id}", produtoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produtoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(produtoId.toString())))
                .andExpect(jsonPath("$.nome", is("Smartphone XYZ Pro")));

        verify(produtoService, times(1)).updateProduto(eq(produtoId), any());
    }

    @Test
    void deveExcluirProduto() throws Exception {
        UUID produtoId = UUID.randomUUID();

        mockMvc = MockMvcBuilders.standaloneSetup(produtoController).build();

        mockMvc.perform(delete("/api/produtos/{id}", produtoId))
                .andExpect(status().isNoContent());

        verify(produtoService, times(1)).deleteProduto(produtoId);
    }
}
