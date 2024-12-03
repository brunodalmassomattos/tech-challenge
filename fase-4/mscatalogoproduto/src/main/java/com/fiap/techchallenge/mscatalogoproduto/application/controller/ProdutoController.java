package com.fiap.techchallenge.mscatalogoproduto.application.controller;

import com.fiap.techchallenge.mscatalogoproduto.application.dtos.produto.AlterProdutoRequest;
import com.fiap.techchallenge.mscatalogoproduto.application.dtos.produto.CategoriaResponse;
import com.fiap.techchallenge.mscatalogoproduto.application.dtos.produto.CreateProdutoRequest;
import com.fiap.techchallenge.mscatalogoproduto.application.dtos.produto.ProdutoResponse;
import com.fiap.techchallenge.mscatalogoproduto.application.usecases.produto.*;
import com.fiap.techchallenge.mscatalogoproduto.domain.entities.Produto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final GetAllProdutosUseCase getAllProdutosUseCase;
    private final GetProdutoUseCase getProdutoUseCase;
    private final SearchProdutosUseCase searchProdutosUseCase;
    private final CreateProdutoUseCase createprodutoUseCase;
    private final AlterProdutoUseCase alterProdutoUseCase;
    private final BaixaEstoqueProdutoUseCase baixaEstoqueProdutoUseCase;
    private final EstornoEstoqueUseCase estornoEstoqueUseCase;
    private final DeleteProdutoUseCase deleteProdutoUseCase;

    public ProdutoController(GetAllProdutosUseCase getAllProdutosUseCase,
                             GetProdutoUseCase getProdutoUseCase,
                             SearchProdutosUseCase searchProdutosUseCase,
                             CreateProdutoUseCase createprodutoUseCase,
                             AlterProdutoUseCase alterProdutoUseCase,
                             BaixaEstoqueProdutoUseCase baixaEstoqueProdutoUseCase,
                             EstornoEstoqueUseCase estornoEstoqueUseCase,
                             DeleteProdutoUseCase deleteProdutoUseCase) {
        this.getAllProdutosUseCase = getAllProdutosUseCase;
        this.getProdutoUseCase = getProdutoUseCase;
        this.searchProdutosUseCase = searchProdutosUseCase;
        this.createprodutoUseCase = createprodutoUseCase;
        this.alterProdutoUseCase = alterProdutoUseCase;
        this.baixaEstoqueProdutoUseCase = baixaEstoqueProdutoUseCase;
        this.estornoEstoqueUseCase = estornoEstoqueUseCase;
        this.deleteProdutoUseCase = deleteProdutoUseCase;
    }

    @PostMapping
    public ResponseEntity<?> createProduto(@RequestBody CreateProdutoRequest request) {
        try {
            request.validate();
            Produto produto = createprodutoUseCase.execute(
                    request.nome(),
                    request.descricao(),
                    request.preco(),
                    request.quantidadeEstoque(),
                    request.idCategoria());

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new ProdutoResponse(
                            produto.getId().toString(),
                            produto.getNome(),
                            produto.getDescricao(),
                            produto.getPreco(),
                            produto.getQtdEstoque(),
                            new CategoriaResponse(
                                    produto.getCategoria().getId().toString(),
                                    produto.getCategoria().getDescricao())));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado ao criar o produto.");
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> alterProduto(
            @PathVariable UUID id,
            @RequestBody AlterProdutoRequest request) {
        try {
            Produto produto = alterProdutoUseCase.execute(AlterProdutoRequest.buildProdutoResquest(id, request));
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ProdutoResponse(
                            produto.getId().toString(),
                            produto.getNome(),
                            produto.getDescricao(),
                            produto.getPreco(),
                            produto.getQtdEstoque(),
                            new CategoriaResponse(
                                    produto.getCategoria().getId().toString(),
                                    produto.getCategoria().getDescricao())));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado ao alterar o produto.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduto(@PathVariable UUID id) {
        try {
            deleteProdutoUseCase.execute(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduto(@PathVariable UUID id) {
        try {
            Produto produto = getProdutoUseCase.execute(id);

            return ResponseEntity.status(HttpStatus.OK).body(
                    new ProdutoResponse(
                            produto.getId().toString(),
                            produto.getNome(),
                            produto.getDescricao(),
                            produto.getPreco(),
                            produto.getQtdEstoque(),
                            new CategoriaResponse(
                                    produto.getCategoria().getId().toString(),
                                    produto.getCategoria().getDescricao())));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> getProdutos() {
        List<Produto> produtos = getAllProdutosUseCase.execute();

        return ResponseEntity.status(HttpStatus.OK).body(
                produtos.stream()
                        .map(p -> new ProdutoResponse(
                                p.getId().toString(),
                                p.getNome(),
                                p.getDescricao(),
                                p.getPreco(),
                                p.getQtdEstoque(),
                                new CategoriaResponse(
                                        p.getCategoria().getId().toString(),
                                        p.getCategoria().getDescricao())
                        ))
                        .toList());
    }

    @GetMapping("/search")
    public List<ProdutoResponse> searchProdutos(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String descricao) {

        List<Produto> produtos = searchProdutosUseCase.execute(nome, descricao);

        return produtos.stream()
                .map(p -> new ProdutoResponse(
                        p.getId().toString(),
                        p.getNome(),
                        p.getDescricao(),
                        p.getPreco(),
                        p.getQtdEstoque(),
                        new CategoriaResponse(
                                p.getCategoria().getId().toString(),
                                p.getCategoria().getDescricao())
                ))
                .toList();
    }

    @@PostMapping("/{id}/baixa-estoque")
    public ResponseEntity<?> baixaQuantidadeProdutoEstoque(
            @PathVariable UUID id,
            @RequestParam Integer quantidade) {
        try {
            Produto produto = baixaEstoqueProdutoUseCase.execute(id, quantidade);

            return ResponseEntity.status(HttpStatus.OK).body(
                    new ProdutoResponse(
                            produto.getId().toString(),
                            produto.getNome(),
                            produto.getDescricao(),
                            produto.getPreco(),
                            produto.getQtdEstoque(),
                            new CategoriaResponse(
                                    produto.getCategoria().getId().toString(),
                                    produto.getCategoria().getDescricao())));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @@PostMapping("/{id}/estorno-estoque")
    public ResponseEntity<?> estornarEstoque(
            @PathVariable UUID id,
            @RequestParam Integer quantidade) {
        try {
            Produto produto = estornoEstoqueUseCase.execute(id, quantidade);

            return ResponseEntity.status(HttpStatus.OK).body(
                    new ProdutoResponse(
                            produto.getId().toString(),
                            produto.getNome(),
                            produto.getDescricao(),
                            produto.getPreco(),
                            produto.getQtdEstoque(),
                            new CategoriaResponse(
                                    produto.getCategoria().getId().toString(),
                                    produto.getCategoria().getDescricao())));

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
