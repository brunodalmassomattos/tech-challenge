// domain/application/service/ClienteService.java
package br.com.fiap.msgerenciamentocliente.domain.application.service;

import br.com.fiap.msgerenciamentocliente.domain.core.entity.Cliente;
import br.com.fiap.msgerenciamentocliente.domain.core.repository.ClienteRepository;
import br.com.fiap.msgerenciamentocliente.infraestructure.handler.GlobalExceptionHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final GlobalExceptionHandler exceptionHandler;

    public ClienteService(ClienteRepository clienteRepository, GlobalExceptionHandler exceptionHandler) {
        this.clienteRepository = clienteRepository;
        this.exceptionHandler = exceptionHandler;
    }

    public List<Cliente> listarClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        if (clientes.isEmpty()) {
            throw new GlobalExceptionHandler.ResourceNotFoundException("Clientes", "Nenhum registro encontrado");
        }
        return clientes;
    }

    @Transactional
    public Cliente criarCliente(Cliente cliente) {
        // Valida o cliente e seus endereços
        exceptionHandler.validateCliente(cliente);

        // Formata o CPF
        if (cliente.getCpf() != null) {
            cliente.setCpf(exceptionHandler.formatCpf(cliente.getCpf()));
        }

        return clienteRepository.save(cliente);
    }

    public Cliente buscarClientePorId(UUID id) {
        exceptionHandler.validateId(id, "Cliente");
        return clienteRepository.findById(id)
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Cliente", id.toString()));
    }

    public Cliente buscarClientePorCpf(String cpf) {
        String cpfFormatado = exceptionHandler.formatCpf(cpf);
        return clienteRepository.findByCpf(cpfFormatado)
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Cliente", "CPF: " + cpf));
    }

    @Transactional
    public Cliente atualizarCliente(UUID id, Cliente clienteAtualizado) {
        exceptionHandler.validateId(id, "Cliente");
        exceptionHandler.validateCliente(clienteAtualizado);

        // Verifica se o cliente existe
        buscarClientePorId(id);

        clienteAtualizado.setId(id);
        return clienteRepository.save(clienteAtualizado);
    }

    @Transactional
    public void deletarCliente(UUID id) {
        exceptionHandler.validateId(id, "Cliente");
        buscarClientePorId(id); // Verifica se existe
        clienteRepository.deleteById(id);
    }
}