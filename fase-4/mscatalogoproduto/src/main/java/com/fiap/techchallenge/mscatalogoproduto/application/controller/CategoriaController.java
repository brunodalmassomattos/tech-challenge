package com.fiap.techchallenge.mscatalogoproduto.application.controller;

import com.fiap.techchallenge.mscatalogoproduto.application.dtos.categoria.CategoriaRequest;
import com.fiap.techchallenge.mscatalogoproduto.application.dtos.categoria.CategoriaResponse;
import com.fiap.techchallenge.mscatalogoproduto.application.usecases.categoria.*;
import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Categoria;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CreateCategoriaUseCase criarCategoriaUseCase;
    private final GetAllCategoriasUseCase getAllCategoriasUseCase;
    private final GetCategoriaUseCase getCategoriaUseCase;
    private final DeleteCategoriaUseCase deleteCategoriaUseCase;
    private final AlterCategoriaUseCase alterCategoriaUseCase;


    public CategoriaController(CreateCategoriaUseCase criarCategoriaUseCase,
                               GetAllCategoriasUseCase getAllCategoriasUseCase,
                               GetCategoriaUseCase getCategoriaUseCase,
                               DeleteCategoriaUseCase deleteCategoriaUseCase,
                               AlterCategoriaUseCase alterCategoriaUseCase) {
        this.criarCategoriaUseCase = criarCategoriaUseCase;
        this.getAllCategoriasUseCase = getAllCategoriasUseCase;
        this.getCategoriaUseCase = getCategoriaUseCase;
        this.deleteCategoriaUseCase = deleteCategoriaUseCase;
        this.alterCategoriaUseCase = alterCategoriaUseCase;
    }

    @PostMapping
    public ResponseEntity<?> criarCategoria(
            @RequestBody
            @Valid CategoriaRequest request) {

        try {
            Categoria categoria = criarCategoriaUseCase.execute(request.descricao());

            return ResponseEntity.status(HttpStatus.CREATED).body(new CategoriaResponse(
                    categoria.getId().toString(),
                    categoria.getDescricao()));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduto(@PathVariable UUID id) {
        try {
            deleteCategoriaUseCase.execute(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> alterProduto(
            @PathVariable UUID id,
            @RequestBody String descricao) {
        try {
            var categoria = alterCategoriaUseCase.execute(new Categoria(id, descricao));

            return ResponseEntity.status(HttpStatus.OK).body(
                    new CategoriaResponse(
                            categoria.getId().toString(),
                            categoria.getDescricao()));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado ao alterar o produto.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoria(@PathVariable UUID id) {
        try {
            Categoria categoria = getCategoriaUseCase.execute(id);

            return ResponseEntity.status(HttpStatus.OK).body(
                    new CategoriaResponse(
                            categoria.getId().toString(),
                            categoria.getDescricao()));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> getCategorias() {
        List<Categoria> categorias = getAllCategoriasUseCase.execute();

        return ResponseEntity.status(HttpStatus.OK).body(
                categorias.stream()
                        .map(p -> new CategoriaResponse(
                                p.getId().toString(),
                                p.getDescricao()))
                        .toList());
    }

}
