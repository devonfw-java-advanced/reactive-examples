package com.capgemini.reactivefibblock;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Service
public class FibService {

    private final RestTemplate fibRestTemplate;


    public Integer fibB(Integer n) {
        return fibRestTemplate.getForObject("http://localhost:8080/fibBlock?n=" + n, Integer.class);
    }

    public Integer fib(Integer max, Integer current, Integer n1, Integer n2) {
        UriComponents url = UriComponentsBuilder.fromUriString("http://localhost:8080/fib")
                .queryParam("max", max)
                .queryParam("current", current)
                .queryParam("n1", n1)
                .queryParam("n2", n2).build();
        return fibRestTemplate.getForObject(url.toUri(), Integer.class);
    }
}
