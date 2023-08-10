package com.reactive.webflux.demo.config;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Service
public class CalculatorHandler {

    public Mono<ServerResponse> additionHandler(ServerRequest request) {
        int a = Integer.parseInt(request.pathVariable("a"));
        int b = Integer.parseInt(request.pathVariable("b"));
        return ServerResponse.ok().bodyValue(a + b);
    }

    public Mono<ServerResponse> subtractionHandler(ServerRequest request) {
        return process(request, (a, b) -> ServerResponse.ok().bodyValue(a - b));
    }

    public Mono<ServerResponse> multiplicationHandler(ServerRequest request) {
        return process(request, (a, b) -> ServerResponse.ok().bodyValue(a * b));
    }

    public Mono<ServerResponse> divisionHandler(ServerRequest request) {
        return process(request, (a, b) -> {
            if (b != 0) {
                return ServerResponse.ok().bodyValue(a / b);
            } else {
                return ServerResponse.badRequest().bodyValue("b can not be 0");
            }
        });
    }

    private Mono<ServerResponse> process(ServerRequest request,
                                         BiFunction<Integer, Integer, Mono<ServerResponse>> opLogic) {
        int a = getValue(request, "a");
        int b = getValue(request, "b");
        return opLogic.apply(a, b);
    }

    private int getValue(ServerRequest request, String key) {
        return Integer.parseInt(request.pathVariable(key));
    }

}
