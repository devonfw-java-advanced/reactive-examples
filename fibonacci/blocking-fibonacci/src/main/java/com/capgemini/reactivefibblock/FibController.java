package com.capgemini.reactivefibblock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FibController {

    private final FibService fibService;

    @GetMapping("/fibBlock")
    public Integer fibBlock(@RequestParam(required = false, defaultValue = "0") Integer n) {

        log.info("Invoking fibbanaci for level {}", n);

        if (n < 2) {
            return n;
        }

        return fibService.fib(n, 2, 2, 1);
    }

    @GetMapping("/fib")
    public Integer fibBlock(Integer max, Integer current, Integer n1, Integer n2) {

        log.info("Invoking fib for level {}", current+1);

        if(max == current + 1) {
            return n1+n2;
        }

        return fibService.fib(max, current+1, n1+n2, n1);
    }

}
