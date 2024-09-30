package br.com.fiap.level3.loadtest;

import br.com.fiap.level3.domain.restaurante.core.model.endereco.EnderecoDTO;
import br.com.fiap.level3.domain.restaurante.core.model.restaurante.RestauranteDTO;
import br.com.fiap.level3.domain.restaurante.core.model.restaurante.AlterarRestauranteDTO;
import br.com.fiap.level3.domain.restaurante.core.model.tiporestaurante.TipoRestauranteDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.apache.commons.lang3.time.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestauranteLoadTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static final int NUMBER_OF_USERS = 100;
    private static final String RESTAURANTE_ID = "d1e5e21b-0283-47b6-b575-5b695586f76d"; // ID do restaurante existente
    private static final String NOME_RESTAURANTE = "Restaurante Teste";


    @Test
    public void testAdicionarRestauranteComCarga() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_USERS);
        List<CompletableFuture<ResponseEntity<String>>> futures = new ArrayList<>();

        StopWatch watch = StopWatch.createStarted();

        for (int i = 0; i < NUMBER_OF_USERS; i++) {
            RestauranteDTO novoRestaurante = new RestauranteDTO(
                    null,
                    NOME_RESTAURANTE + " " + i,
                    null,
                    0,
                    false,
                    new TipoRestauranteDTO(null, null),
                    new EnderecoDTO(null, null,null,null,null,null,null));
            HttpEntity<RestauranteDTO> request = new HttpEntity<>(novoRestaurante);

            CompletableFuture<ResponseEntity<String>> future = CompletableFuture.supplyAsync(() ->
                            restTemplate.postForEntity("/v1/restaurantes", request, String.class),
                    executorService
            );
            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        watch.stop();

        long totalTimeMillis = watch.getTime();
        double totalTimeSeconds = totalTimeMillis / 1000.0;
        double averageTimeSeconds = totalTimeSeconds / NUMBER_OF_USERS;

        System.out.printf("Adicionar Restaurantes - Total time: %.3f seconds%n", totalTimeSeconds);
        System.out.printf("Adicionar Restaurantes - Average time per request: %.3f seconds%n", averageTimeSeconds);

        executorService.shutdown();
    }

    @Test
    public void testDeletarRestauranteComCarga() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_USERS);
        List<CompletableFuture<ResponseEntity<String>>> futures = new ArrayList<>();

        StopWatch watch = StopWatch.createStarted();

        for (int i = 0; i < NUMBER_OF_USERS; i++) {
            CompletableFuture<ResponseEntity<String>> future = CompletableFuture.supplyAsync(() ->
                            restTemplate.exchange("/v1/restaurantes/" + RESTAURANTE_ID, HttpMethod.DELETE, null, String.class),
                    executorService
            );
            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        watch.stop();

        long totalTimeMillis = watch.getTime();
        double totalTimeSeconds = totalTimeMillis / 1000.0;
        double averageTimeSeconds = totalTimeSeconds / NUMBER_OF_USERS;

        System.out.printf("Deletar Restaurantes - Total time: %.3f seconds%n", totalTimeSeconds);
        System.out.printf("Deletar Restaurantes - Average time per request: %.3f seconds%n", averageTimeSeconds);

        executorService.shutdown();
    }
}
