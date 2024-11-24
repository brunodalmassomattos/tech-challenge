package br.com.fiap.msgerenciamentocliente.domain.infraestructure.api;

import br.com.fiap.msgerenciamentocliente.domain.application.service.ClienteService;
import br.com.fiap.msgerenciamentocliente.domain.core.entity.Cliente;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "API para gerenciamento de clientes")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Operation(
            summary = "Criar novo cliente",
            description = "Cria um novo cliente no sistema com seus dados pessoais e endereços"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Cliente criado com sucesso",
                    content = @Content(schema = @Schema(implementation = Cliente.class))),
            @ApiResponse(responseCode = "400",
                    description = "Dados inválidos fornecidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "422",
                    description = "Erro de validação nos dados fornecidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<Cliente> criarCliente(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do cliente a ser criado",
                    required = true,
                    content = @Content(schema = @Schema(implementation = Cliente.class))
            )
            @RequestBody Cliente cliente) {
        Cliente novoCliente = clienteService.criarCliente(cliente);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novoCliente.getId())
                .toUri();
        return ResponseEntity.created(location).body(novoCliente);
    }

    @Operation(
            summary = "Listar todos os clientes",
            description = "Retorna a lista de todos os clientes cadastrados no sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Lista de clientes retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = Cliente.class))),
            @ApiResponse(responseCode = "404",
                    description = "Nenhum cliente encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes() {
        return ResponseEntity.ok(clienteService.listarClientes());
    }

    @Operation(
            summary = "Buscar cliente por ID",
            description = "Retorna os dados de um cliente específico através do seu ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Cliente encontrado com sucesso",
                    content = @Content(schema = @Schema(implementation = Cliente.class))),
            @ApiResponse(responseCode = "404",
                    description = "Cliente não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarClientePorId(
            @Parameter(description = "ID do cliente", required = true)
            @PathVariable UUID id) {
        Cliente cliente = clienteService.buscarClientePorId(id);
        return ResponseEntity.ok(cliente);
    }

    @Operation(
            summary = "Buscar cliente por CPF",
            description = "Retorna os dados de um cliente específico através do seu CPF"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Cliente encontrado com sucesso",
                    content = @Content(schema = @Schema(implementation = Cliente.class))),
            @ApiResponse(responseCode = "404",
                    description = "Cliente não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "CPF inválido",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Cliente> buscarClientePorCpf(
            @Parameter(
                    description = "CPF do cliente (formato: XXX.XXX.XXX-XX)",
                    required = true,
                    example = "123.456.789-00"
            )
            @PathVariable String cpf) {
        Cliente cliente = clienteService.buscarClientePorCpf(cpf);
        return ResponseEntity.ok(cliente);
    }

    @Operation(
            summary = "Atualizar cliente",
            description = "Atualiza os dados de um cliente existente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Cliente atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = Cliente.class))),
            @ApiResponse(responseCode = "404",
                    description = "Cliente não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "422",
                    description = "Erro de validação nos dados fornecidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizarCliente(
            @Parameter(description = "ID do cliente", required = true)
            @PathVariable UUID id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Novos dados do cliente",
                    required = true,
                    content = @Content(schema = @Schema(implementation = Cliente.class))
            )
            @RequestBody Cliente clienteAtualizado) {
        Cliente cliente = clienteService.atualizarCliente(id, clienteAtualizado);
        return ResponseEntity.ok(cliente);
    }

    @Operation(
            summary = "Deletar cliente",
            description = "Remove um cliente do sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente deletado com sucesso"),
            @ApiResponse(responseCode = "404",
                    description = "Cliente não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(
            @Parameter(description = "ID do cliente", required = true)
            @PathVariable UUID id) {
        clienteService.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }
}