package com.capgemini.reactivewebflux.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebFluxConfiguration {
    @Bean
    public WebClient localClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8080/")
                .build();
    }
}
