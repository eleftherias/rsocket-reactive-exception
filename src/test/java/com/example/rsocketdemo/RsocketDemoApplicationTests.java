package com.example.rsocketdemo;

import io.rsocket.exceptions.ApplicationErrorException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.rsocket.context.LocalRSocketServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Flux;

@TestPropertySource(properties = "spring.rsocket.server.port=0")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RsocketDemoApplicationTests {
    @Autowired
    RSocketRequester.Builder requester;

    @LocalRSocketServerPort
    int port;

    @Test
    public void retrieveFluxWhenDataFluxAndInvalidRouteThenApplicationErrorException() {
        RSocketRequester requester = this.requester
                .connectTcp("localhost", this.port)
                .block();

        Assertions.assertThatCode(
                () -> requester
                        .route("does-not-exist")
                        .data(Flux.just("a", "b", "c"), String.class)
                        .retrieveFlux(String.class)
                        .collectList()
                        .block())
                .isInstanceOf(ApplicationErrorException.class);
    }
}
