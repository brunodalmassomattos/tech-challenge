package br.com.fiap.msgerenciamentocliente.domain.infraestructure.api;

import br.com.fiap.msgerenciamentocliente.domain.application.service.EnderecoService;
import br.com.fiap.msgerenciamentocliente.domain.core.entity.Endereco;
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
@RequestMapping("/api/enderecos")
@Tag(name = "Endereços", description = "API para gerenciamento de endereços")
public class EnderecoController {
    private final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @Operation(
            summary = "Listar todos os endereços",
            description = "Retorna a lista de todos os endereços cadastrados"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Lista de endereços retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = Endereco.class))),
            @ApiResponse(responseCode = "404",
                    description = "Nenhum endereço encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<List<Endereco>> listarEnderecos() {
        return ResponseEntity.ok(enderecoService.listarEnderecos());
    }

    @Operation(
            summary = "Buscar endereço por ID",
            description = "Retorna um endereço específico pelo seu ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Endereço encontrado com sucesso",
                    content = @Content(schema = @Schema(implementation = Endereco.class))),
            @ApiResponse(responseCode = "404",
                    description = "Endereço não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Endereco> buscarEnderecoPorId(
            @Parameter(description = "ID do endereço", required = true)
            @PathVariable UUID id) {
        Endereco endereco = enderecoService.buscarEnderecoPorId(id);
        return ResponseEntity.ok(endereco);
    }

    @Operation(
            summary = "Atualizar endereço",
            description = "Atualiza os dados de um endereço existente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Endereço atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = Endereco.class))),
            @ApiResponse(responseCode = "404",
                    description = "Endereço não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "422",
                    description = "Erro de validação nos dados fornecidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<Endereco> atualizarEndereco(
            @Parameter(description = "ID do endereço", required = true)
            @PathVariable UUID id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Novos dados do endereço",
                    required = true,
                    content = @Content(schema = @Schema(implementation = Endereco.class))
            )
            @RequestBody Endereco enderecoAtualizado) {
        Endereco endereco = enderecoService.atualizarEndereco(id, enderecoAtualizado);
        return ResponseEntity.ok(endereco);
    }

    @Operation(
            summary = "Deletar endereço",
            description = "Remove um endereço do sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Endereço deletado com sucesso"),
            @ApiResponse(responseCode = "404",
                    description = "Endereço não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEndereco(
            @Parameter(description = "ID do endereço", required = true)
            @PathVariable UUID id) {
        enderecoService.deletarEndereco(id);
        return ResponseEntity.noContent().build();
    }
}