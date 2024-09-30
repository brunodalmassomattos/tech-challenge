package br.com.fiap.level3.loadtest;

import br.com.fiap.level3.domain.tiporestaurante.core.model.TipoRestauranteDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.apache.commons.lang3.time.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TipoRestauranteLoadTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static final int NUMBER_OF_USERS = 100;
    private static final String DESCRICAO = "Restaurante Italiano";
    private static final UUID TIPO_RESTAURANTE_ID = UUID.fromString("43ee22ea-164a-4408-a817-597f8a89fab3");

    @Test
    public void testListarTiposRestauranteComCarga() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_USERS);
        List<CompletableFuture<ResponseEntity<List<TipoRestauranteDTO>>>> futures = new ArrayList<>();

        StopWatch watch = StopWatch.createStarted();

        for (int i = 0; i < NUMBER_OF_USERS; i++) {
            CompletableFuture<ResponseEntity<List<TipoRestauranteDTO>>> future = CompletableFuture.supplyAsync(() ->
                            restTemplate.exchange(
                                    "/v1/tipo-restaurante",
                                    HttpMethod.GET,
                                    null,
                                    new ParameterizedTypeReference<List<TipoRestauranteDTO>>() {}
                            ),
                    executorService
            );
            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        watch.stop();

        long totalTimeMillis = watch.getTime();
        double totalTimeSeconds = totalTimeMillis / 1000.0;
        double averageTimeSeconds = totalTimeSeconds / NUMBER_OF_USERS;

        System.out.printf("Listar Tipo de Restaurante por Descrição - Total time: %.3f seconds%n", totalTimeSeconds);
        System.out.printf("Listar Tipo de Restaurante por Descrição - Average time per request: %.3f seconds%n", averageTimeSeconds);

        executorService.shutdown();
    }

    @Test
    public void testBuscarTipoRestaurantePorIdComCarga() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_USERS);
        List<CompletableFuture<ResponseEntity<TipoRestauranteDTO>>> futures = new ArrayList<>();

        StopWatch watch = StopWatch.createStarted();

        for (int i = 0; i < NUMBER_OF_USERS; i++) {
            CompletableFuture<ResponseEntity<TipoRestauranteDTO>> future = CompletableFuture.supplyAsync(() ->
                            restTemplate.getForEntity("/v1/tipo-restaurante/" + TIPO_RESTAURANTE_ID, TipoRestauranteDTO.class),
                    executorService
            );
            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        watch.stop();

        long totalTimeMillis = watch.getTime();
        double totalTimeSeconds = totalTimeMillis / 1000.0;
        double averageTimeSeconds = totalTimeSeconds / NUMBER_OF_USERS;

        System.out.printf("Buscar Tipo de Restaurante por ID - Total time: %.3f seconds%n", totalTimeSeconds);
        System.out.printf("Buscar Tipo de Restaurante por ID - Average time per request: %.3f seconds%n", averageTimeSeconds);

        executorService.shutdown();
    }

    @Test
    public void testCriarTipoRestauranteComCarga() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_USERS);
        List<CompletableFuture<ResponseEntity<TipoRestauranteDTO>>> futures = new ArrayList<>();

        StopWatch watch = StopWatch.createStarted();

        for (int i = 0; i < NUMBER_OF_USERS; i++) {
            TipoRestauranteDTO novoTipo = new TipoRestauranteDTO(null, DESCRICAO);
            HttpEntity<TipoRestauranteDTO> request = new HttpEntity<>(novoTipo);

            CompletableFuture<ResponseEntity<TipoRestauranteDTO>> future = CompletableFuture.supplyAsync(() ->
                            restTemplate.postForEntity("/v1/tipo-restaurante", request, TipoRestauranteDTO.class),
                    executorService
            );
            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        watch.stop();

        long totalTimeMillis = watch.getTime();
        double totalTimeSeconds = totalTimeMillis / 1000.0;
        double averageTimeSeconds = totalTimeSeconds / NUMBER_OF_USERS;

        System.out.printf("Criar Tipo de Restaurante - Total time: %.3f seconds%n", totalTimeSeconds);
        System.out.printf("Criar Tipo de Restaurante - Average time per request: %.3f seconds%n", averageTimeSeconds);

        executorService.shutdown();
    }

    @Test
    public void testAtualizarTipoRestauranteComCarga() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_USERS);
        List<CompletableFuture<ResponseEntity<Void>>> futures = new ArrayList<>();

        StopWatch watch = StopWatch.createStarted();

        for (int i = 0; i < NUMBER_OF_USERS; i++) {
            TipoRestauranteDTO tipoAtualizado = new TipoRestauranteDTO(TIPO_RESTAURANTE_ID.toString(), DESCRICAO + " Atualizado");
            HttpEntity<TipoRestauranteDTO> request = new HttpEntity<>(tipoAtualizado);

            CompletableFuture<ResponseEntity<Void>> future = CompletableFuture.supplyAsync(() ->
                            restTemplate.exchange("/v1/tipo-restaurante/" + TIPO_RESTAURANTE_ID, HttpMethod.PUT, request, Void.class),
                    executorService
            );
            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        watch.stop();

        long totalTimeMillis = watch.getTime();
        double totalTimeSeconds = totalTimeMillis / 1000.0;
        double averageTimeSeconds = totalTimeSeconds / NUMBER_OF_USERS;

        System.out.printf("Atualizar Tipo de Restaurante - Total time: %.3f seconds%n", totalTimeSeconds);
        System.out.printf("Atualizar Tipo de Restaurante - Average time per request: %.3f seconds%n", averageTimeSeconds);

        executorService.shutdown();
    }

    @Test
    public void testDeletarTipoRestauranteComCarga() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_USERS);
        List<CompletableFuture<ResponseEntity<Void>>> futures = new ArrayList<>();

        StopWatch watch = StopWatch.createStarted();

        for (int i = 0; i < NUMBER_OF_USERS; i++) {
            CompletableFuture<ResponseEntity<Void>> future = CompletableFuture.supplyAsync(() ->
                            restTemplate.exchange("/v1/tipo-restaurante/" + TIPO_RESTAURANTE_ID, HttpMethod.DELETE, null, Void.class),
                    executorService
            );
            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        watch.stop();

        long totalTimeMillis = watch.getTime();
        double totalTimeSeconds = totalTimeMillis / 1000.0;
        double averageTimeSeconds = totalTimeSeconds / NUMBER_OF_USERS;

        System.out.printf("Deletar Tipo de Restaurante - Total time: %.3f seconds%n", totalTimeSeconds);
        System.out.printf("Deletar Tipo de Restaurante - Average time per request: %.3f seconds%n", averageTimeSeconds);

        executorService.shutdown();
    }
}
