package com.reactive.webflux.demo.config;

import com.reactive.webflux.demo.dto.MultiplyRequestDto;
import com.reactive.webflux.demo.dto.ResponseDto;
import com.reactive.webflux.demo.exception.InputValidationException;
import com.reactive.webflux.demo.service.ReactiveMathService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RequestHandler {
     ReactiveMathService mathService;

    public Mono<ServerResponse> squareHandler(ServerRequest serverRequest){
        int input = Integer.parseInt(serverRequest.pathVariable("input"));
        Mono<ResponseDto> resMono = mathService.findSquare(input);
        return ServerResponse.ok().body(resMono, ResponseDto.class);
    }

    public Mono<ServerResponse> tableHandler(ServerRequest serverRequest){
        int input = Integer.parseInt(serverRequest.pathVariable("input"));
        Flux<ResponseDto> resFlux = mathService.multiplicationTable(input);
        return ServerResponse.ok().body(resFlux, ResponseDto.class);
    }

    public Mono<ServerResponse> tableStreamHandler(ServerRequest serverRequest){
        int input = Integer.parseInt(serverRequest.pathVariable("input"));
        Flux<ResponseDto> resFlux = mathService.multiplicationTable(input);
        return ServerResponse.ok()
                .contentType(MediaType.valueOf(MediaType.TEXT_EVENT_STREAM_VALUE))
                .body(resFlux, ResponseDto.class);
    }

    public Mono<ServerResponse> multiplyHandler(ServerRequest serverRequest){
        Mono<MultiplyRequestDto> requestDtoMono = serverRequest.bodyToMono(MultiplyRequestDto.class);
        Mono<ResponseDto> resMono = mathService.multiply(requestDtoMono);
        return ServerResponse.ok()
                .body(resMono, ResponseDto.class);
    }

    public Mono<ServerResponse> squareHandlerWithValidation(ServerRequest serverRequest){
        int input = Integer.parseInt(serverRequest.pathVariable("input"));
        if(input < 10 || input > 20){
            return Mono.error(new InputValidationException(input));
        }
        Mono<ResponseDto> resMono = mathService.findSquare(input);
        return ServerResponse.ok()
                .body(resMono, ResponseDto.class);
    }
}
