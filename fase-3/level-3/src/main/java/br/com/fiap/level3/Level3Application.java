package br.com.fiap.level3;

import br.com.fiap.level3.infrastructure.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
        RestauranteDomainConfig.class,
        TipoRestauranteDomainConfig.class,
        AvaliacaoConfig.class,
        ReservaConfig.class,
        UsuarioDomainConfig.class
})
public class Level3Application {

    public static void main(String[] args) {
        SpringApplication.run(Level3Application.class, args);
    }

}
