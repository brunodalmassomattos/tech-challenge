package br.com.fiap.level3.loadtest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;

import br.com.fiap.level3.domain.avaliacao.core.domain.model.avaliacao.AvaliacaoDTO;

import org.apache.commons.lang3.time.StopWatch;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AvaliacaoLoadTest {

    @Autowired
    private TestRestTemplate restTemplate;
    
    private static final int NUMBER_OF_USERS = 100;
    private static final UUID AVALIACAO_ID = UUID.fromString("6c57c0d6-b919-4a7e-91e0-01a5cbe38f18");
    private static final Integer NOTA = 8;
    private static final String COMENTARIO = "Teste";
    private static final String RESTAURANTE_ID = "7ceb223e-0bdf-47a5-bd2d-1cff74715b1e";
    private static final String USUARIO_ID = "51c130b1-fe42-4e3f-a9ef-36db7f146961";

    
    @Test
    public void testAdicionarAvaliacaoComCarga() {
        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_USERS);
        
        List<CompletableFuture<ResponseEntity<String>>> futures = new ArrayList<>();
        
        StopWatch watch = StopWatch.createStarted();
        
        for (int i = 0; i < NUMBER_OF_USERS; i++) {
            AvaliacaoDTO avaliacaoDto = new AvaliacaoDTO(null, NOTA, COMENTARIO, RESTAURANTE_ID, USUARIO_ID);
            HttpEntity<AvaliacaoDTO> request = new HttpEntity<>(avaliacaoDto);
            
            CompletableFuture<ResponseEntity<String>> future = CompletableFuture.supplyAsync(() -> {
                try {
                    return restTemplate.postForEntity("/avaliacao", request, String.class);
                } catch (Exception e) {
                    e.printStackTrace();  // Log de erro
                    return null;  // Ou um valor de fallback
                }
            }, executorService);
            
            futures.add(future);
        }
        
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        watch.stop();

        long totalTimeMillis = watch.getTime();
        double totalTimeSeconds = totalTimeMillis / 1000.0;
        double averageTimeSeconds = totalTimeSeconds / NUMBER_OF_USERS;
        
        System.out.printf("Adicionar Avaliacao - Total time: %.3f seconds%n", totalTimeSeconds);
        System.out.printf("Adicionar Avaliacao - Average time per request: %.3f seconds%n", averageTimeSeconds);

        executorService.shutdown();
    }

    @Test
    public void testBuscarAvaliacaoPorIdComCarga() {
        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_USERS);
        
        List<CompletableFuture<ResponseEntity<AvaliacaoDTO>>> futures = new ArrayList<>();
        
        StopWatch watch = StopWatch.createStarted();
        
        for (int i = 0; i < NUMBER_OF_USERS; i++) {
            
            CompletableFuture<ResponseEntity<AvaliacaoDTO>> future = CompletableFuture.supplyAsync(() -> {
                try {
                    return restTemplate.getForEntity("/avaliacao/" + AVALIACAO_ID.toString(), AvaliacaoDTO.class);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }, executorService);
            
            futures.add(future);
        }
        
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        watch.stop();

        long totalTimeMillis = watch.getTime();
        double totalTimeSeconds = totalTimeMillis / 1000.0;
        double averageTimeSeconds = totalTimeSeconds / NUMBER_OF_USERS;
        
        System.out.printf("Buscar Avaliacao - Total time: %.3f seconds%n", totalTimeSeconds);
        System.out.printf("Buscar Avaliacao - Average time per request: %.3f seconds%n", averageTimeSeconds);

        executorService.shutdown();
    }
    
}
