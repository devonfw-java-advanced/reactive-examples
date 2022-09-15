package com.capgemini.reactivewebflux.controller;

import com.capgemini.reactivewebflux.documents.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ChangeStreamEvent;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@RestController
public class StreamController {

    final ReactiveMongoTemplate reactiveMongoTemplate;

    @GetMapping(value = "/product_stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> streamNewProducts() {
        return reactiveMongoTemplate.changeStream(
                User.class)
                .watchCollection("users")
                .listen()
                .mapNotNull(ChangeStreamEvent::getBody);
    }
}
