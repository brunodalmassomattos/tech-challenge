package br.com.fiap.msgerenciamentocliente.domain.application.service;

import br.com.fiap.msgerenciamentocliente.domain.core.entity.Cliente;
import br.com.fiap.msgerenciamentocliente.domain.core.entity.Endereco;
import br.com.fiap.msgerenciamentocliente.domain.core.repository.ClienteRepository;
import br.com.fiap.msgerenciamentocliente.infraestructure.handler.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private GlobalExceptionHandler exceptionHandler;

    @InjectMocks
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
    void criarCliente_DeveRetornarClienteCriado() {
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        doNothing().when(exceptionHandler).validateCliente(any(Cliente.class));

        Cliente clienteCriado = clienteService.criarCliente(cliente);

        assertNotNull(clienteCriado);
        assertEquals(cliente.getNome(), clienteCriado.getNome());
        assertEquals(cliente.getCpf(), clienteCriado.getCpf());
        verify(clienteRepository).save(any(Cliente.class));
        verify(exceptionHandler).validateCliente(any(Cliente.class));
    }

    @Test
    void buscarClientePorId_DeveRetornarCliente() {
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        doNothing().when(exceptionHandler).validateId(any(UUID.class), anyString());

        Cliente clienteEncontrado = clienteService.buscarClientePorId(clienteId);

        assertNotNull(clienteEncontrado);
        assertEquals(cliente.getId(), clienteEncontrado.getId());
        verify(clienteRepository).findById(clienteId);
    }

    @Test
    void buscarClientePorId_DeveRetornarExcecao_QuandoClienteNaoEncontrado() {
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());
        doNothing().when(exceptionHandler).validateId(any(UUID.class), anyString());

        assertThrows(GlobalExceptionHandler.ResourceNotFoundException.class, () -> {
            clienteService.buscarClientePorId(clienteId);
        });
    }

    @Test
    void listarClientes_DeveRetornarListaDeClientes() {
        when(clienteRepository.findAll()).thenReturn(Arrays.asList(cliente));

        var clientes = clienteService.listarClientes();

        assertNotNull(clientes);
        assertFalse(clientes.isEmpty());
        assertEquals(1, clientes.size());
        verify(clienteRepository).findAll();
    }

    @Test
    void atualizarCliente_DeveRetornarClienteAtualizado() {
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        doNothing().when(exceptionHandler).validateId(any(UUID.class), anyString());
        doNothing().when(exceptionHandler).validateCliente(any(Cliente.class));

        Cliente clienteAtualizado = clienteService.atualizarCliente(clienteId, cliente);

        assertNotNull(clienteAtualizado);
        assertEquals(cliente.getId(), clienteAtualizado.getId());
        verify(clienteRepository).save(any(Cliente.class));
    }

    @Test
    void deletarCliente_DeveDeletarClienteComSucesso() {
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        doNothing().when(clienteRepository).deleteById(clienteId);
        doNothing().when(exceptionHandler).validateId(any(UUID.class), anyString());

        assertDoesNotThrow(() -> clienteService.deletarCliente(clienteId));
        verify(clienteRepository).deleteById(clienteId);
    }
}