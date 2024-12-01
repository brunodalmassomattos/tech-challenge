package br.com.fiap.msgerenciamentocliente.infraestructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Gerenciamento de Clientes")
                        .description("API para gerenciamento de clientes e endereços")
                        .version("1.0")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")))
                .tags(List.of(
                        new Tag().name("Clientes").description("Operações relacionadas a clientes"),
                        new Tag().name("Endereços").description("Operações relacionadas a endereços")
                ));
    }
}

//http://localhost:8080/swagger-ui.html