package com.capgemini.reactivewebflux.service;

import com.capgemini.reactivewebflux.to.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Service
public class SlowService {

    private final WebClient localClient;

    public Flux<Product> getSlowProducts() {
        return localClient.get()
                .uri("/slow")
                .retrieve()
                .bodyToFlux(Product.class);
    }
}
