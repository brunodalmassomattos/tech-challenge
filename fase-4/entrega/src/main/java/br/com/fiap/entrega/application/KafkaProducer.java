package br.com.fiap.entrega.application;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
	private final KafkaTemplate<String, String> kafkaTemplate;

	public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	//Enviar mensagme "event":"Rastreio","notaFiscal":"3114552996","codigoRastreio":"2837128321832"}
	public void sendMessage(String topic, String message) {
		kafkaTemplate.send(topic, message);
		System.out.println("Mensagem enviada para o tópico: " + topic);
	}
}
