package com.food.trucks.controller;

import com.food.trucks.service.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class HelloWorldWebFluxController {

    @Autowired
    private HelloWorldService helloWorldService;

    @GetMapping(value = "/hello")
    public Mono<String> getResource() {
        return helloWorldService.getResource();
    }
}
