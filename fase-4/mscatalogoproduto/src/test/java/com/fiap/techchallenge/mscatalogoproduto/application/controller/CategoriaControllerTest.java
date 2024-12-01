package com.fiap.techchallenge.mscatalogoproduto.application.controller;

import com.fiap.techchallenge.mscatalogoproduto.application.dtos.categoria.CategoriaRequest;
import com.fiap.techchallenge.mscatalogoproduto.application.usecases.categoria.*;
import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Categoria;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoriaController.class)
@EnableWebMvc
class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateCategoriaUseCase criarCategoriaUseCase;

    @MockBean
    private GetAllCategoriasUseCase getAllCategoriasUseCase;

    @MockBean
    private GetCategoriaUseCase getCategoriaUseCase;

    @MockBean
    private DeleteCategoriaUseCase deleteCategoriaUseCase;

    @MockBean
    private AlterCategoriaUseCase alterCategoriaUseCase;

    @Nested
    class testesCriacaoCategoria {
        @Test
        public void testCriarCategoria() throws Exception {
            CategoriaRequest categoriaRequest = new CategoriaRequest("Categoria Teste");
            Categoria categoria = new Categoria(UUID.randomUUID(), "Categoria Teste");

            // Mock do caso de uso para criação de categoria
            when(criarCategoriaUseCase.execute("Categoria Teste")).thenReturn(categoria);

            // Executa a chamada
            mockMvc.perform(post("/categorias")
                            .contentType("application/json")
                            .content("{\"descricao\": \"Categoria Teste\"}"))
                    .andExpect(status().isCreated());
        }

        @Test
        public void testCriarCategoria_EntityNotFoundException() throws Exception {
            // Quando o método executar lança a EntityNotFoundException
            when(criarCategoriaUseCase.execute("Categoria Teste")).thenThrow(new EntityNotFoundException("Categoria não encontrada"));

            mockMvc.perform(post("/categorias")
                            .contentType("application/json")
                            .content("{\"descricao\": \"Categoria Teste\"}"))
                    .andExpect(status().isNotFound());
        }

        @Test
        public void testCriarCategoria_RuntimeException() throws Exception {
            // Quando o método executar lança uma RuntimeException
            when(criarCategoriaUseCase.execute("Categoria Teste")).thenThrow(new RuntimeException("Erro inesperado"));

            mockMvc.perform(post("/categorias")
                            .contentType("application/json")
                            .content("{\"descricao\": \"Categoria Teste\"}"))
                    .andExpect(status().isInternalServerError());
        }
    }

    @Nested
    class testesDeleteCategoria {

        @Test
        public void testDeleteCategoria() throws Exception {
            UUID categoriaId = UUID.randomUUID();
            doNothing().when(deleteCategoriaUseCase).execute(categoriaId);

            mockMvc.perform(delete("/categorias/{id}", categoriaId))
                    .andExpect(status().isNoContent());
        }

        @Test
        public void testDeleteCategoria_EntityNotFoundException() throws Exception {
            UUID categoriaId = UUID.randomUUID();

            // Quando o deleteCategoriaUseCase lançar a EntityNotFoundException
            doThrow(new EntityNotFoundException("Categoria não encontrada")).when(deleteCategoriaUseCase).execute(categoriaId);

            mockMvc.perform(delete("/categorias/{id}", categoriaId))
                    .andExpect(status().isNotFound());
        }

        @Test
        public void testDeleteCategoria_RuntimeException() throws Exception {
            UUID categoriaId = UUID.randomUUID();

            // Quando o método executar lança uma RuntimeException
            doThrow(new RuntimeException("Erro inesperado")).when(deleteCategoriaUseCase).execute(categoriaId);

            mockMvc.perform(delete("/categorias/{id}", categoriaId)
                            .contentType("application/json")
                            .content("{\"descricao\": \"Categoria Teste\"}"))
                    .andExpect(status().isInternalServerError());
        }
    }

    @Nested
    class testeAlteracaoCategoria {
        @Test
        public void testAlterCategoria() throws Exception {
            UUID categoriaId = UUID.randomUUID();
            String novaDescricao = "Categoria Alterada";
            Categoria categoria = new Categoria(categoriaId, novaDescricao);

            when(alterCategoriaUseCase.execute(any(Categoria.class))).thenReturn(categoria);

            mockMvc.perform(patch("/categorias/{id}", categoriaId)
                            .contentType("application/json")
                            .content("{\"descricao\": \"Categoria Alterada\"}"))
                    .andExpect(status().isOk());
        }

        @Test
        public void testAlterCategoria_RuntimeException() throws Exception {
            UUID categoriaId = UUID.randomUUID();
            String novaDescricao = "Categoria Alterada";

            // Simula uma RuntimeException quando o caso de uso de alteração for chamado
            when(alterCategoriaUseCase.execute(any(Categoria.class))).thenThrow(new RuntimeException("Erro inesperado ao alterar categoria"));

            mockMvc.perform(patch("/categorias/{id}", categoriaId)
                            .contentType("application/json")
                            .content("{\"descricao\": \"Categoria Alterada\"}"))
                    .andExpect(status().isInternalServerError());
        }
    }

    @Nested
    class testePesquisaCategoria {

        @Test
        public void testGetCategoria() throws Exception {
            UUID categoriaId = UUID.randomUUID();
            Categoria categoria = new Categoria(categoriaId, "Categoria Teste");

            when(getCategoriaUseCase.execute(categoriaId)).thenReturn(categoria);

            mockMvc.perform(get("/categorias/{id}", categoriaId))
                    .andExpect(status().isOk());
        }

        @Test
        public void testGetCategoria_EntityNotFoundException() throws Exception {
            UUID categoriaId = UUID.randomUUID();

            // Quando o getCategoriaUseCase lançar a EntityNotFoundException
            when(getCategoriaUseCase.execute(categoriaId)).thenThrow(new EntityNotFoundException("Categoria não encontrada"));

            mockMvc.perform(get("/categorias/{id}", categoriaId))
                    .andExpect(status().isNotFound());
        }
    }

    @Test
    public void testGetCategorias() throws Exception {
        Categoria categoria1 = new Categoria(UUID.randomUUID(), "Categoria 1");
        Categoria categoria2 = new Categoria(UUID.randomUUID(), "Categoria 2");

        List<Categoria> categorias = Arrays.asList(categoria1, categoria2);
        when(getAllCategoriasUseCase.execute()).thenReturn(categorias);

        mockMvc.perform(get("/categorias"))
                .andExpect(status().isOk());
    }

}