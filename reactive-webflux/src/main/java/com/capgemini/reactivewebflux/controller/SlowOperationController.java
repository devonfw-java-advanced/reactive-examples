package com.capgemini.reactivewebflux.controller;

import com.capgemini.reactivewebflux.service.SlowService;
import com.capgemini.reactivewebflux.to.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SlowOperationController {

    private final SlowService slowService;

    @GetMapping("/slow")
    public Flux<Product> favourites() {
        log.info("Starting request slow");
        Flux<Product> productFlux = Flux.just(
                new Product("Prod1", 19.99),
                new Product("Prod2", 29.99)
        ).delaySequence(Duration.ofSeconds(3));
        log.info("Ending request slow request");
        return productFlux;
    }

    @GetMapping(value = "/slowTest", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Product> slowTest1() {
        log.info("Starting request slowTest");
        Flux<Product> productFlux = slowService.getSlowProducts()
                .mergeWith(slowService.getSlowProducts());
        log.info("Ending request slowTest");
        return productFlux;
    }


    @GetMapping("/slowTest2")
    public Flux<Product> slowTest2() {
        log.info("Starting request slowTest2");
        for (int i = 0; i < 1000; i++) {
            slowService.getSlowProducts()
                    .doOnNext(p -> log.info("Get response"))
                    .subscribe();
        }
        log.info("Ending request slowTest2");
        return Flux.empty();
    }

    @GetMapping("/slowTest3")
    public Flux<Product> slowTest3() {
        log.info("Starting request slowTest3");
        Flux<Product> slowProducts = slowService.getSlowProducts();
        for (int i = 0; i < 100; i++) {
            slowProducts = slowProducts.mergeWith(slowService.getSlowProducts());
        }
        log.info("Ending request slowTest3");
        return slowProducts;
    }
}
