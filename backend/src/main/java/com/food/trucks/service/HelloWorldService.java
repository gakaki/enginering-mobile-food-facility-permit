package com.food.trucks.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Slf4j
@Component
public class HelloWorldService {

    @Autowired
    private WebClient webClient;

    public Mono<String> getResource() {
//https://howtodoinjava.com/spring-webflux/retry-with-spring-webclient/
        return webClient.get()
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(2))
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(2)));
    }


}

