package br.com.fiap.msgerenciamentocliente.domain.infraestructure.api;

import br.com.fiap.msgerenciamentocliente.domain.core.entity.Cliente;
import br.com.fiap.msgerenciamentocliente.domain.core.entity.Endereco;
import br.com.fiap.msgerenciamentocliente.domain.application.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")  // Isso vai usar o application-test.yml
public class ClienteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClienteService clienteService;

    private Cliente cliente;
    private Endereco endereco;
    private UUID clienteId;

    @BeforeEach
    void setUp() {
        clienteId = UUID.randomUUID();
        endereco = new Endereco();
        endereco.setId(UUID.randomUUID());
        endereco.setRua("Rua Teste");
        endereco.setBairro("Bairro Teste");
        endereco.setNumero(123);
        endereco.setCidade("Cidade Teste");
        endereco.setEstado("SP");
        endereco.setCep("12345-678");

        cliente = new Cliente();
        cliente.setId(clienteId);
        cliente.setNome("João Teste");
        cliente.setCpf("123.456.789-00");
        cliente.setDataNascimento(new Date());
        cliente.setEmail("joao@teste.com");
        cliente.setTelefone("11999999999");
        cliente.setEndereco(endereco);
    }

    @Test
    void criarCliente_DeveRetornarStatus201() throws Exception {
        when(clienteService.criarCliente(any(Cliente.class))).thenReturn(cliente);

        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(clienteId.toString()))
                .andExpect(jsonPath("$.nome").value(cliente.getNome()));
    }

    @Test
    void listarClientes_DeveRetornarStatus200() throws Exception {
        when(clienteService.listarClientes()).thenReturn(Arrays.asList(cliente));

        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(clienteId.toString()))
                .andExpect(jsonPath("$[0].nome").value(cliente.getNome()));
    }

    @Test
    void buscarClientePorId_DeveRetornarStatus200() throws Exception {
        when(clienteService.buscarClientePorId(clienteId)).thenReturn(cliente);

        mockMvc.perform(get("/api/clientes/{id}", clienteId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(clienteId.toString()))
                .andExpect(jsonPath("$.nome").value(cliente.getNome()));
    }

    @Test
    void atualizarCliente_DeveRetornarStatus200() throws Exception {
        when(clienteService.atualizarCliente(any(UUID.class), any(Cliente.class))).thenReturn(cliente);

        mockMvc.perform(put("/api/clientes/{id}", clienteId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(clienteId.toString()))
                .andExpect(jsonPath("$.nome").value(cliente.getNome()));
    }

    @Test
    void deletarCliente_DeveRetornarStatus204() throws Exception {
        mockMvc.perform(delete("/api/clientes/{id}", clienteId))
                .andExpect(status().isNoContent());
    }
}