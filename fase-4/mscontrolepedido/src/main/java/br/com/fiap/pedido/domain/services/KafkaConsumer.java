package br.com.fiap.pedido.domain.services;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class KafkaConsumer {

	private final PedidoService pedidoService;
	
    @KafkaListener(topics = "rastreio", groupId = "java-group-1")
    public void listen(ConsumerRecord<String, String> record) {
    	pedidoService.atualizaCodigoEntrega(record.value());
    }
}
