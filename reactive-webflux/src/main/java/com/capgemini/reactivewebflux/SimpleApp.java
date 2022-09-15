package com.capgemini.reactivewebflux;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.Disposable;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class SimpleApp {

    public static void main(String[] args) throws IOException {

        example10();

        for (int i = 1; i <= 5; i++) {
            log.info("Waiting {} seconds", i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // ignore
            }
        }
    }

    public static void example1() {
        Mono<String> map = Mono.just(1)
                .log()
                .map(String::valueOf);

        map.subscribe(log::info);
//        map.subscribe(log::info);
//        map.subscribe(log::info);
    }

    public static void example2() {
        Flux.just(1, 2, 3, 4)
//                .log()
                .publishOn(Schedulers.boundedElastic())
//                .map(String::valueOf)
//                .subscribe(log::info);
                .subscribe(SimpleApp::waitFor);
    }

    public static void example3() {
        Flux.just(1, 2, 3, 4)
                .log()
                .subscribe(new Subscriber<>() {

                    Subscription s;
                    @Override
                    public void onSubscribe(Subscription s) {
                        this.s = s;
                        s.request(1);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        log.info("Next element {}", integer);
                        s.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public static void example4() {
        Flux.just(1, 2, 3, 4)
                .log()
                .map(i -> i * 2)
                .zipWith(Flux.range(0, Integer.MAX_VALUE).log(),
                        (one, two) -> String.format("First Flux: %d, Second Flux: %d", one, two))
                .subscribe(log::info);
    }

    public static void example5() {
        ConnectableFlux<Object> publish = Flux.create(fluxSink -> {
            while(true) {
                fluxSink.next(System.currentTimeMillis());
            }
        }).subscribeOn(Schedulers.parallel()).publish();

        publish.connect();
        publish.map(String::valueOf)
                .subscribe(log::info);

//        Flux<Object> flux = Flux.create(fluxSink -> {
//            while(true) {
//                fluxSink.next(System.currentTimeMillis());
//            }
//        }).subscribeOn(Schedulers.parallel());
//
//        flux.map(String::valueOf)
//                .subscribe(log::info);
    }

    public static void example6() {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> waitFor(2));

        Mono.fromFuture(completableFuture)
//                .subscribeOn(Schedulers.boundedElastic())
                .map(String::valueOf)
                .subscribe(log::info);
    }

    public static void example7() {

        Flux<Integer> flux1 = Flux.just(1, 2)
                .flatMap(value -> Flux.just(value * 2, value * 4, value))
                .flatMap(value2 -> Mono.just(value2 * 2));

        flux1
                .map(String::valueOf)
                .subscribe(log::info);
    }

    public static void example8() {
        Flux<Integer> flux1 = Flux.just(1, 2)
                .flatMap(value -> Mono.just(value * 2))
                .flatMap(value2 -> Mono.just(value2 * 2));

        flux1
                .map(String::valueOf)
                .subscribe(log::info);
    }

    public static void example9() {
        Mono.defer(() -> Mono.just(waitFor(3)))
            .map(String::valueOf)
                .subscribeOn(Schedulers.boundedElastic());
//                .subscribe(log::info);
    }

    public static void example10() {
        Mono.just(waitFor(3))
                .map(String::valueOf)
                .subscribeOn(Schedulers.boundedElastic());
//                .subscribe(log::info);

//        Mono.create(sink -> {
//            waitFor(3);
//            sink.success("Test");
//        })
//                .subscribeOn(Schedulers.boundedElastic())
//                .map(String::valueOf)
//                .subscribe(log::info);

    }

    private static int waitFor(int seconds) {
        log.info("Wait For {} seconds", seconds);
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            // ignore
        }

        return seconds;
    }
}
