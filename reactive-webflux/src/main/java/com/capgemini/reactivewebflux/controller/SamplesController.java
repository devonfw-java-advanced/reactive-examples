package com.capgemini.reactivewebflux.controller;

import com.capgemini.reactivewebflux.documents.User;
import com.capgemini.reactivewebflux.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@Slf4j
@RequiredArgsConstructor
public class SamplesController {

    final UserRepository userRepository;

    @GetMapping("/sample1")
    public void sample1() {
        log.info("Starting request");
        Flux<Integer> integerFlux = Flux.range(1, 20)
//                .log()
                .delayElements(Duration.ofMillis(200));
        integerFlux.subscribe(num -> log.info("Processing number: " + num));
        integerFlux.subscribe(num -> log.info("Processing number 2: " + num));
        log.info("Ending request");
    }

    @GetMapping("/sample2")
    public void sample12() {
        Flux.range(1, 20)
                .log();
        log.info("Ending request");
    }

    @GetMapping("/sample3")
    public Mono<User> sample3() throws InterruptedException {
        log.info("Starting request");
        Flux.just("test@capgemini.pl", "test2@capgemini.pl", "test3@capgemini.pl", "test4@capgemini.pl")
                .map(User::new)
                .flatMap(userRepository::save)
                .thenMany(userRepository.findAll())
                .subscribe(user -> log.info(user.toString()));

        Mono<User> save = userRepository.save(new User("cooo@coo.pl"));

//        save.subscribe();
//        Mono<User> userMono = save.delayElement(Duration.ofSeconds(5));
        log.info("Ending request");
        return save;
    }

    @GetMapping("/clear")
    public Mono<Void> clear() {
        return userRepository.deleteAll();
    }

    @GetMapping("/users")
    public Flux<User> users() {
        log.info("Starting request");
        Flux<User> all = userRepository.findAll().log();
        log.info("Ending request");
        return all;
    }

    @GetMapping("/users2")
    public Flux<User> users2() {
        log.info("Starting request");
        Flux<User> userFlux = userRepository.findAll()
                .delayElements(Duration.ofSeconds(1));
        log.info("Ending request");
        return userFlux;
    }

    @GetMapping(value = "/users3", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<User> users3() {
        log.info("Starting request");
        Flux<User> userFlux = userRepository.findAll()
                .log()
                .delayElements(Duration.ofSeconds(1));
        log.info("Ending request");
        return userFlux;
    }

    @GetMapping(value = "/users4", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<User> users4() {
        log.info("Starting request");
        Flux<User> userFlux = userRepository.findAll()
                .log()
                .delayElements(Duration.ofSeconds(1));
        log.info("Ending request");
        return userFlux;
    }
}
