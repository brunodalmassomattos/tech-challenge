package br.com.fiap.mslogistica.application.gateway;

import br.com.fiap.mslogistica.application.dto.ClienteDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.http.HttpMethod;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.http.outbound.HttpRequestExecutingMessageHandler;
import org.springframework.messaging.MessageChannel;

import java.util.Map;

@Configuration
public class GatewayConfiguration {

    private static final String BASE_URL = "http://localhost:8080/api/clientes";

    @Bean
    public MessageChannel apiClienteChannel() {
        DirectChannel directChannel = new DirectChannel();
        directChannel.setFailover(false);
        return directChannel;
    }

    @Bean
    public IntegrationFlow clienteFlow(){
        return IntegrationFlow
                       .from("apiClienteChannel")
                       .handle(httpRequestHandler())
                       .get();
    }

    @Bean
    @ServiceActivator(inputChannel = "apiClienteChannel")
    public HttpRequestExecutingMessageHandler httpRequestHandler() {
        HttpRequestExecutingMessageHandler handler = new HttpRequestExecutingMessageHandler(BASE_URL + "/{id}");
        SpelExpressionParser spelExpressionParser = new SpelExpressionParser();
        handler.setHttpMethod(HttpMethod.GET);
        handler.setExpectedResponseType(ClienteDto.class);
        handler.setUriVariableExpressions(Map.of("id", spelExpressionParser.parseExpression("payload")));
        return handler;
    }
}
