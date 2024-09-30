package br.com.fiap.level3.loadtest;

import br.com.fiap.level3.domain.reserva.core.model.enums.StatusEnum;
import br.com.fiap.level3.domain.reserva.core.model.reserva.ReservaDTO;
import br.com.fiap.level3.domain.reserva.core.model.reserva.ReservaRestauranteDTO;
import br.com.fiap.level3.domain.reserva.core.model.reserva.AtualizarStatusDTO;
import br.com.fiap.level3.domain.reserva.mocks.ReservaDTOTestMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservaLoadTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static final int NUMBER_OF_USERS = 100;
    private static final UUID RESTAURANTE_ID = UUID.fromString("67208951-4a07-4226-b2b6-c500c78d4ba7");
    private static final UUID RESERVA_ID = UUID.fromString("4bcf70fb-e7e4-44da-b369-ca3795adaf2a");

    @Test
    public void testListarReservasPorRestauranteComCarga() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_USERS);
        List<CompletableFuture<ResponseEntity<ReservaRestauranteDTO>>> futures = new ArrayList<>();

        StopWatch watch = StopWatch.createStarted();

        for (int i = 0; i < NUMBER_OF_USERS; i++) {
            CompletableFuture<ResponseEntity<ReservaRestauranteDTO>> future = CompletableFuture.supplyAsync(() ->
                            restTemplate.getForEntity("/reservas/restaurante/" + RESTAURANTE_ID, ReservaRestauranteDTO.class),
                    executorService
            );
            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        watch.stop();

        long totalTimeMillis = watch.getTime();
        double totalTimeSeconds = totalTimeMillis / 1000.0;
        double averageTimeSeconds = totalTimeSeconds / NUMBER_OF_USERS;

        System.out.printf("Listar Reservas Por Restaurante - Total time: %.3f seconds%n", totalTimeSeconds);
        System.out.printf("Listar Reservas Por Restaurante - Average time per request: %.3f seconds%n", averageTimeSeconds);

        executorService.shutdown();
    }

    @Test
    public void testListarReservaPorIdComCarga() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_USERS);
        List<CompletableFuture<ResponseEntity<ReservaDTO>>> futures = new ArrayList<>();

        StopWatch watch = StopWatch.createStarted();

        for (int i = 0; i < NUMBER_OF_USERS; i++) {
            CompletableFuture<ResponseEntity<ReservaDTO>> future = CompletableFuture.supplyAsync(() ->
                            restTemplate.getForEntity("/reservas/" + RESERVA_ID, ReservaDTO.class),
                    executorService
            );
            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        watch.stop();

        long totalTimeMillis = watch.getTime();
        double totalTimeSeconds = totalTimeMillis / 1000.0;
        double averageTimeSeconds = totalTimeSeconds / NUMBER_OF_USERS;

        System.out.printf("Listar Reserva Por ID - Total time: %.3f seconds%n", totalTimeSeconds);
        System.out.printf("Listar Reserva Por ID - Average time per request: %.3f seconds%n", averageTimeSeconds);

        executorService.shutdown();
    }

    @Test
    public void testAtualizarStatusReservaComCarga() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_USERS);
        List<CompletableFuture<ResponseEntity<ReservaDTO>>> futures = new ArrayList<>();

        StopWatch watch = StopWatch.createStarted();

        for (int i = 0; i < NUMBER_OF_USERS; i++) {
            CompletableFuture<ResponseEntity<ReservaDTO>> future = CompletableFuture.supplyAsync(() -> {
                AtualizarStatusDTO atualizarStatusDTO = new AtualizarStatusDTO(StatusEnum.CONFIRMADA);
                HttpEntity<AtualizarStatusDTO> requestEntity = new HttpEntity<>(atualizarStatusDTO);
                return restTemplate.exchange(
                        "/reservas/" + RESERVA_ID + "/status",
                        HttpMethod.PUT,
                        requestEntity,
                        ReservaDTO.class
                );
            }, executorService);
            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        watch.stop();

        long totalTimeMillis = watch.getTime();
        double totalTimeSeconds = totalTimeMillis / 1000.0;
        double averageTimeSeconds = totalTimeSeconds / NUMBER_OF_USERS;

        System.out.printf("Atualizar Status Reserva - Total time: %.3f seconds%n", totalTimeSeconds);
        System.out.printf("Atualizar Status Reserva - Average time per request: %.3f seconds%n", averageTimeSeconds);

        executorService.shutdown();
    }

    @Test
    void deveCriarNovaReservaComCarga() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_USERS);
        List<CompletableFuture<ResponseEntity<ReservaDTO>>> futures = new ArrayList<>();

        StopWatch watch = StopWatch.createStarted();

        for (int i = 0; i < NUMBER_OF_USERS; i++) {
            CompletableFuture<ResponseEntity<ReservaDTO>> future = CompletableFuture.supplyAsync(() -> {
                ReservaDTO reservaDTO = ReservaDTOTestMock.getReservaDTO();
                HttpEntity<ReservaDTO> requestEntity = new HttpEntity<>(reservaDTO);
                return restTemplate.exchange(
                        "/reservas",
                        HttpMethod.POST,
                        requestEntity,
                        ReservaDTO.class
                );
            }, executorService);
            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        watch.stop();

        long totalTimeMillis = watch.getTime();
        double totalTimeSeconds = totalTimeMillis / 1000.0;
        double averageTimeSeconds = totalTimeSeconds / NUMBER_OF_USERS;

        System.out.printf("Cadastrar nova reserva Reserva - Total time: %.3f seconds%n", totalTimeSeconds);
        System.out.printf("Cadastrar nova reserva Reserva - Average time per request: %.3f seconds%n", averageTimeSeconds);

        executorService.shutdown();
    }
}