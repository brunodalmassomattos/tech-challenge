package br.com.fiap.level3;

import br.com.fiap.level3.infrastructure.RestauranteConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
		RestauranteConfig.class
})
public class Level3Application {

	public static void main(String[] args) {
		SpringApplication.run(Level3Application.class, args);
	}

}