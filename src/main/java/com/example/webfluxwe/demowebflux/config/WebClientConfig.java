package com.example.webfluxwe.demowebflux.config;

import com.example.webfluxwe.demowebflux.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {

    @Autowired
    private AuthService authService;


    @Bean
    public WebClient miVersionWebClient() {
        System.out.println("###########___Adentro de myCustomWebClient__");
        return WebClient.builder()
                .filter(ExchangeFilterFunction.ofRequestProcessor(request -> Mono.just(ClientRequest.from(request)
                        .headers(httpHeaders -> httpHeaders.setBearerAuth(authService.getToken())).build())))
//				.filter(ExchangeFilterFunction
//						.ofResponseProcessor(response -> exchangeFilterResponseProcessor(response)))
                .build();
    }
}

