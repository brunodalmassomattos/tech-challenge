package br.com.fiap.newparquimetro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NewParquimetroApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewParquimetroApplication.class, args);
	}

}
