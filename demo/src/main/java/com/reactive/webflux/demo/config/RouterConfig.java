package com.reactive.webflux.demo.config;

import com.reactive.webflux.demo.dto.InputFailedValidationResponse;
import com.reactive.webflux.demo.exception.InputValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Configuration
@RequiredArgsConstructor
public class RouterConfig {
    private final RequestHandler requestHandler;

    @Bean
    public RouterFunction<ServerResponse> highLevelRouter(){
        return RouterFunctions.route()
                .path("router", this::serverResponseRouterFunction)
                .build();
    }

//    @Bean
    public RouterFunction<ServerResponse> serverResponseRouterFunction(){
        return RouterFunctions.route()
                .GET("square/{input}", RequestPredicates.path("*/1?"),requestHandler::squareHandler)
                .GET("table/{input}", requestHandler::tableHandler)
                .GET("table/{input}/stream", requestHandler::tableStreamHandler)
                .POST("multiply", requestHandler::multiplyHandler)
                .GET("square/{input}/validation", requestHandler::squareHandlerWithValidation)
                .onError(InputValidationException.class, exceptionHandler())
                .build();
    }

//    @Bean
//    public RouterFunction<ServerResponse> serverResponseRouterFunction(){
//        return RouterFunctions.route()
//                .GET("router/square/{input}", requestHandler::squareHandler)
//                .GET("router/table/{input}", requestHandler::tableHandler)
//                .GET("router/table/{input}/stream", requestHandler::tableStreamHandler)
//                .POST("router/multiply", requestHandler::multiplyHandler)
//                .GET("router/square/{input}/throw", requestHandler::squareHandlerWithValidation)
//                .onError(InputValidationException.class, exceptionHandler())
//        .build();
//    }

    private BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> exceptionHandler(){
        return (err, req) -> {
            InputValidationException ex = (InputValidationException) err;
            InputFailedValidationResponse response = new InputFailedValidationResponse();
            response.setInput(ex.getInput());
            response.setMessage(ex.getMessage());
            response.setErrorCode(ex.getErrorCode());
            return ServerResponse.badRequest().bodyValue(response);
        };
    }
}
