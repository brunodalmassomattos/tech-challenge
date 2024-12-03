package br.com.fiap.entrega.application.event;

import br.com.fiap.entrega.application.dto.PedidoConsumerDto;
import br.com.fiap.entrega.domain.service.EntregaService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class PedidoConsumer {

    private final EntregaService entregaService;

    private final static String EVENTO = "Pagamento Confirmado";

    public PedidoConsumer(EntregaService entregaService) {
        this.entregaService = entregaService;
    }

    @Scheduled(cron = "0 0/10 * * * *", zone = "America/Sao_Paulo")
    @KafkaListener(topics = "pedidos", groupId = "java-group-1")
    public void listen(ConsumerRecord<String, PedidoConsumerDto> record) {

        if (Objects.nonNull(record.value()) && record.value().event().equals(EVENTO)) {
            log.info(String.format("Recebendo evento: %s", record.value().event()));
            log.info(String.format("Criando entrega para nota fisca: %s", record.value().notaFiscal()));
            entregaService.criarEntrega(record.value());
        } else {
            log.info(String.format("Não foi possível criar entrega para a mensagem recebida: %s", record.value()));
        }
    }
}
