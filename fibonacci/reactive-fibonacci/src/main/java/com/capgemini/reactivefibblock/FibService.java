package com.capgemini.reactivefibblock;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class FibService {

    private final WebClient fibWebClient;

    public Mono<Integer> fib(Integer max, Integer current, Integer n1, Integer n2) {
        UriComponents url = UriComponentsBuilder.fromUriString("http://localhost:8080/fib")
                .queryParam("max", max)
                .queryParam("current", current)
                .queryParam("n1", n1)
                .queryParam("n2", n2).build();
        return fibWebClient
                .get()
                .uri(url.toUri())
                .retrieve()
                .bodyToMono(Integer.class);
    }
}
