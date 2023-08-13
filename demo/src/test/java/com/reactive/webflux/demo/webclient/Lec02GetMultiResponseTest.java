package com.reactive.webflux.demo.webclient;

import com.reactive.webflux.demo.dto.ResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class Lec02GetMultiResponseTest extends BaseTest {

    @Autowired
    private WebClient webClient;

    @Test
    public void fluxTest(){

        Flux<ResponseDto> responseFlux = this.webClient
                .get()
                .uri("reactive-math/table/{number}", 5)
                .retrieve()
                .bodyToFlux(ResponseDto.class)
                .doOnNext(System.out::println);

        StepVerifier.create(responseFlux)
                .expectNextCount(10)
                .verifyComplete();

    }

    @Test
    public void fluxStreamTest(){
        Flux<ResponseDto> responseFlux = this.webClient
                .get()
                .uri("reactive-math/table/{number}/stream", 5)
                .retrieve()
                .bodyToFlux(ResponseDto.class)
                .doOnNext(System.out::println);

        StepVerifier.create(responseFlux)
                .expectNextCount(100)
                .verifyComplete();

    }

}
