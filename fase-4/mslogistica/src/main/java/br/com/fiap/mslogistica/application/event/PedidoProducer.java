package br.com.fiap.mslogistica.application.event;

import br.com.fiap.mslogistica.application.dto.PedidoProducerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PedidoProducer {
	private final KafkaTemplate<String, PedidoProducerDto> kafkaTemplate;

	public PedidoProducer(KafkaTemplate<String, PedidoProducerDto> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void sendMessage(String topic, PedidoProducerDto message) {
		log.info(String.format("Entrega da nota fiscal %s criada com Código de rastreio: %s",
				message.notaFiscal(), message.codigoRastreio()));
		kafkaTemplate.send(topic, message);
	}
}
