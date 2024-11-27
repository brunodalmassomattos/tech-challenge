package br.com.fiap.funcionalidadeDeCargaDeProdutos.infrastructure.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info().title("API de Carga de Produtos")
                        .description("Documentação da API de Carga de Produtos")
                        .version("v1.0"))
                .externalDocs(new ExternalDocumentation()
                        .description("Mais informações")
                        .url("http://springdoc.org"));
    }

    @Bean
    public GroupedOpenApi produtosApi() {
        return GroupedOpenApi.builder()
                .group("produtos")
                .pathsToMatch("/api/produtos/**")
                .build();
    }

    @Bean
    public GroupedOpenApi categoriasApi() {
        return GroupedOpenApi.builder()
                .group("categorias")
                .pathsToMatch("/api/categorias/**")
                .build();
    }
}

//http://localhost:8080/swagger-ui.html