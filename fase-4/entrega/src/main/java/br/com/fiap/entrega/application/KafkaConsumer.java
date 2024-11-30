package br.com.fiap.entrega.application;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    //Fazer para ouvir o topico e receber mensagems {"event":"Pagamento Confirmado","notaFiscal":"3114552996","idCliente":"1fa5f9c2-7d12-4e11-8149-7f9e859aec33"}
    //Criar listener para o rastreio

    @KafkaListener(topics = "rastreio", groupId = "java-group-1")
    public void listen(ConsumerRecord<String, String> record) {
        System.out.println("Mensagem recebida: " + record.value());
    }
}
