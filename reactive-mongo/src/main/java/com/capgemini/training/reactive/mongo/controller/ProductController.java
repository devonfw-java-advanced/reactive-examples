package com.capgemini.training.reactive.mongo.controller;

import com.capgemini.training.reactive.mongo.model.Product;
import com.capgemini.training.reactive.mongo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ChangeStreamEvent;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {

    final ReactiveMongoTemplate reactiveMongoTemplate;

    final ProductRepository productRepository;

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Product> streamNewProducts() {
        return reactiveMongoTemplate.changeStream(
                Product.class)
                .watchCollection("product")
                .listen()
                .mapNotNull(ChangeStreamEvent::getBody);
    }

    @PutMapping
    public Mono<Product> addProduct() {
        return productRepository.insert(Product.builder()
                        .name("Test " + System.currentTimeMillis())
                        .price(10.99)
                .build());
    }

    @GetMapping
    public Flux<Product> getProducts() {
        return productRepository.findAll();
    }
}
