package com.example.rsocketdemo;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class SampleController {

    @MessageMapping("message")
    public Mono<String> message() {
        return Mono.just("Hello");
    }
}
