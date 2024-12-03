package br.com.fiap.pedido;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class Level4PedidoApplication {

	public static void main(String[] args) {
		SpringApplication.run(Level4PedidoApplication.class, args);
	}

}
