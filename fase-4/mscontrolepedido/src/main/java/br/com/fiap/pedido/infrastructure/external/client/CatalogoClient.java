package br.com.fiap.pedido.infrastructure.external.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "catalogoClient", url = "${service.catalogo.url}")
public interface CatalogoClient {
	
	@GetMapping("/{produtoId}")
	String buscarProdutoPorId(@PathVariable("produtoId") String produtoId);
	
	@PostMapping("/{produtoId}/baixa-estoque")
	void baixaEstoque(@PathVariable("produtoId") String produtoId, @RequestParam("quantidade") int quantidade);
	
	@PostMapping("/{produtoId}/estorno-estoque")
	void estornoEstoque(@PathVariable("produtoId") String produtoId, @RequestParam("quantidade") int quantidade);
	
}
